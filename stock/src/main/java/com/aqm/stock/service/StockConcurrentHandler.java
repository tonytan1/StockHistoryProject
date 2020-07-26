package com.aqm.stock.service;

import java.util.List;
import java.util.concurrent.Future;

import com.aqm.stock.DO.StockHistoryDO;

public interface StockConcurrentHandler {
    
    Future<String> updateStockHandler(List<StockHistoryDO> list, Integer index);
}