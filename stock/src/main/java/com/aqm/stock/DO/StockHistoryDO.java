package com.aqm.stock.DO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@Validated
@ApiModel("StockHistoryDO")
public class StockHistoryDO {
    @ApiModelProperty("symbol, String, not blank")
    @NotBlank(message = "symbol should not be blank")
    private String symbol;

    @ApiModelProperty("close price, number, positive and not blank")
    @DecimalMin(value="0", inclusive = true, message="close should be a positive number")
    private BigDecimal close;

    @ApiModelProperty("high price, number, positive and not blank")
    @DecimalMin(value="0", inclusive = true, message="high should be a positive number")
    private BigDecimal high;

    @ApiModelProperty("low price, number, positive and not blank") 
    @DecimalMin(value="0", inclusive = true, message="low should be a positive number")
    private BigDecimal low;

    @ApiModelProperty("open price, number, positive and not blank")
    @DecimalMin(value="0", inclusive = true, message="open should be a positive number")
    private BigDecimal open;

    @ApiModelProperty("adjusted closing price, number, positive and not blank")
    @DecimalMin(value="0", inclusive = true, message="adjClose should be a positive number")
    private BigDecimal adjClose;

    @ApiModelProperty("volume, number, positive and not blank")
    @DecimalMin(value="0", inclusive = true, message="volume should be a positive number")
    private int volume;

    @ApiModelProperty("date, String, format: yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;//TODO: need proper date validation 

    public static List<StockHistoryDO> validateDateAndReturnInvalidData(List<StockHistoryDO> list) {
        List<StockHistoryDO> result = new ArrayList<>();
        for(StockHistoryDO sh : list) {
            if(sh.getDate() == null) {
                result.add(sh);
            }
        }
        return result;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(BigDecimal adjClose) {
        this.adjClose = adjClose;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StockHistoryDO [adjClose=" + adjClose + ", close=" + close + ", date=" + date + ", high=" + high
                + ", low=" + low + ", open=" + open + ", symbol=" + symbol + ", volume=" + volume + "]";
    }
}