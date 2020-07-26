package com.aqm.stock.service;

import java.util.Date;
import java.util.List;

import com.aqm.stock.entity.StockHistory;
import com.aqm.stock.DO.StockHistoryDO;

public interface StockService {
    public Boolean updateStockHistoryConcurrent(List<StockHistoryDO> stocks);
    // public Boolean updateStockHistory(List<StockHistoryDO> stocks);
    public List<StockHistory> getStockHistory(String symbol, Date start, Date end);
    public Boolean deleteStockHistory(String symbol);
}