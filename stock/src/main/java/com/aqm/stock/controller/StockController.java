package com.aqm.stock.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.aqm.stock.annotation.AuthToken;
import com.aqm.stock.entity.StockHistory;
import com.aqm.stock.model.ResponseEntity;
import com.aqm.stock.DO.StockHistoryDO;
import com.aqm.stock.DO.ValidList;
import com.aqm.stock.service.StockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@RestController
@EnableEurekaClient
@Api(tags = "Stock history API", description = "Provide APIs of get, update, delete for stock history")
public class StockController {
    Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @AuthToken
    @PostMapping(value = "/aqm/get/{symbol}")
    @ApiOperation("get stock history by symbol and date range(inclusive)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Authorization token", example = "AQM666", required = true),
            @ApiImplicitParam(paramType = "path", name = "symbol", value = "symbol name", example = "MSFT", required = true),
            @ApiImplicitParam(name = "start", value = "startDate: yyyy-MM-dd", example = "1987-01-30", required = true),
            @ApiImplicitParam(name = "end", value = "endDate: yyyy-MM-dd", example = "1987-01-30", required = true) })
    // TODO: pageable ?
    public ResponseEntity getStockHistory(@PathVariable("symbol") String symbol,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        List<StockHistory> list = stockService.getStockHistory(symbol, startDate, endDate);
        return new ResponseEntity(200, "success", list);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Authorization token", example = "AQM666", required = true),
            @ApiImplicitParam(paramType = "body", name = "records", required = true, dataTypeClass=ValidList.class,
            examples = @Example(value = {//TODO: json example in swagger UI not working
                    @ExampleProperty(mediaType = "application/json", value = "[{'symbol':'MSFT','close':0.25,'high':0.25,'low':0.24,'open':0.24,'adjClose':0.16,'volume':104169600,'date':'1987-01-30'},{'symbol':'MSFT','close':0.25,'high':0.25,'low':0.24,'open':0.24,'adjClose':0.16,'volume':104169600,'date':'1987-01-31'}]") })) })
    @AuthToken
    @PostMapping(value = "/aqm/update")
    @ApiOperation("update stock history by json ")
    public ResponseEntity updateStockHistory(@RequestBody @Valid ValidList<StockHistoryDO> records) {
        List<StockHistoryDO> failedList = StockHistoryDO.validateDateAndReturnInvalidData(records);
        if(!failedList.isEmpty()) return new ResponseEntity(405, "InvalidDateInJsonBody", failedList);
        Boolean res = stockService.updateStockHistoryConcurrent(records);
        return new ResponseEntity(200, "success", res);

    }

    @AuthToken
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "Authorization token", example = "AQM666", required = true),
            @ApiImplicitParam(paramType = "path", name = "symbol", value = "symbol name", example = "MSFT", required = true) })
    @PostMapping(value = "/aqm/delete/{symbol}")
    @ApiOperation("delete stock history records by symbol ")
    public ResponseEntity deleteStockHistory(@PathVariable("symbol") String symbol) {
        Boolean res = stockService.deleteStockHistory(symbol);
        return new ResponseEntity(200, "success", res);

    }
}