package com.aqm.stock.repository;

import java.util.Date;
import java.util.List;

import com.aqm.stock.entity.StockHistory;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends CrudRepository<StockHistory, Long> {

    @Query(value = "select a from StockHistory a where a.symbol= :symbol and a.date>=:start and a.date<=:end order by a.date")
    List<StockHistory> findBySymbolAndDates(@Param("symbol") String symbol, @Param("start")Date start, @Param("end")Date end);
    
    StockHistory findBySymbolAndDate(String symbol, Date date);

    List<StockHistory> findBySymbol(String symbol); // FOR TEST

    @Modifying 
    //clear the underlying persistence context after executing the modifying query
    @Query(value = "delete from StockHistory a where a.symbol= :symbol")
    void deleteAll(@Param("symbol")String symbol);

}
