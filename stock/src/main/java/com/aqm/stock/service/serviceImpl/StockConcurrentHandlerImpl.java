package com.aqm.stock.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.aqm.stock.DO.StockHistoryDO;

import com.aqm.stock.entity.StockHistory;
import com.aqm.stock.repository.StockRepository;
import com.aqm.stock.service.StockConcurrentHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service("stockConcurrentHandler")
public class StockConcurrentHandlerImpl implements StockConcurrentHandler {
    private Logger logger = LoggerFactory.getLogger(StockConcurrentHandlerImpl.class);

    @Autowired
    StockRepository stockRepository;

    @Value("${stock.update.batch.size}")
    private Integer batchSize;

    @Async
    @Override
    public Future<String> updateStockHandler(List<StockHistoryDO> list, Integer sequence) {
        Future<String> result = new AsyncResult<String>("");
        if (null != list && list.size() > 0) {
            List<StockHistory> rsList = new ArrayList<>();
            int lastGroupSize = list.size() % batchSize;
            for (StockHistoryDO record : list) {
                try {
                    if(record.getDate() == null) {
                        logger.error("Input date is null: {}", record.toString());
                        result = new AsyncResult<String>(record.toString());
                        continue;
                    }
                    StockHistory stockHistory = stockRepository.findBySymbolAndDate(record.getSymbol(),
                            record.getDate());
                    if (stockHistory == null) {
                        stockHistory = new StockHistory(record);
                    } else {
                        Long id = stockHistory.getId();
                        stockHistory = new StockHistory(record);
                        stockHistory.setId(id);
                    }
                    rsList.add(stockHistory);
                    if (!rsList.isEmpty() && (rsList.size() == batchSize || rsList.size() == lastGroupSize)) {
                        stockRepository.saveAll(rsList);
                        rsList.clear();
                    }
                } catch (Exception e) {
                    // record the fail case
                    result = new AsyncResult<String>("fail,time=" + System.currentTimeMillis() + ",thread id="
                            + Thread.currentThread().getName() + ", thread sequence=" + sequence);
                    continue;
                }
            }
        }
        return result;
    }

}