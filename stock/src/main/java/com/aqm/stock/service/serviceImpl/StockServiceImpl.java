package com.aqm.stock.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.aqm.stock.entity.StockHistory;
import com.aqm.stock.DO.StockHistoryDO;
import com.aqm.stock.repository.StockRepository;
import com.aqm.stock.service.StockConcurrentHandler;
import com.aqm.stock.service.StockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("stockService")
public class StockServiceImpl implements StockService {
    private Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockConcurrentHandler stockConcurrentHandler;

    @Value("${stock.update.batch.size}")
    private Integer batchSize;

    @Override
    @Transactional(rollbackFor = Exception.class)
    // before this method, suggested to call a validation method 
    // that could return invalid data to check
    public Boolean updateStockHistoryConcurrent(List<StockHistoryDO> records) {
        if (records == null || records.isEmpty())
            return true;

        int listSize = records.size();

        int spiltSize = batchSize;
        List<Future<String>> futureList = new ArrayList<Future<String>>();
        if (spiltSize > listSize) {
            spiltSize = listSize;
        }
        int threadSeq = 0;
        int listStart = 0;
        while (listStart < listSize) {
            int listEnd = listStart + spiltSize;
            if (listEnd > listSize)
                listEnd = listSize;
            logger.info("thread sequence: {} , totoal: {}, start:{}, end:{}", threadSeq, listSize, listStart, listEnd);
            List<StockHistoryDO> subList = records.subList(listStart, listEnd);
            futureList.add(stockConcurrentHandler.updateStockHandler(subList, threadSeq++));
            listStart = listEnd;
        }

        String str = "";
        Boolean result = true;
        for (Future<String> future : futureList) {
            try {
                str = future.get().toString();
                if (str != null && !str.isEmpty()) {
                    logger.error("[Error]current thread id =" + Thread.currentThread().getName() + ",result=" + str);
                    result = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Exception: {}", e);
            }
        }
        return result;

    }

    // @Override
    // @Transactional(rollbackFor = Exception.class)
    // public Boolean updateStockHistory(List<StockHistoryDO> records) {
    //     for (StockHistoryDO record : records) {
    //         logger.info("date:{}", record.getDate());

    //         if(record.getDate() == null) return false;

    //         StockHistory stockHistory = stockRepository.findBySymbolAndDate(record.getSymbol(), record.getDate());
    //         if (stockHistory == null) {
    //             stockHistory = new StockHistory(record);
    //         } else {
    //             Long id = stockHistory.getId();
    //             stockHistory = new StockHistory(record);
    //             stockHistory.setId(id);
    //         }
    //         stockRepository.save(stockHistory);
    //     }
    //     return true;
    // }

    @Override
    public List<StockHistory> getStockHistory(String symbol, Date start, Date end) {
        if (start.after(end) || start.after(new Date()))
            return Collections.emptyList();
        logger.info("getStockHistory : {} {} {}", symbol, start, end);
        List<StockHistory> result = stockRepository.findBySymbolAndDates(symbol, start, end);
        logger.info("getStockHistory : {}", result.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteStockHistory(String symbol) {
        List<StockHistory> list = stockRepository.findBySymbol(symbol);
        if (list != null && list.size() > 0) {
            stockRepository.deleteAll(symbol);
        }
        return true;
    }

}