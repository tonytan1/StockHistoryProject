package com.aqm.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockHistoryDO {
    private String symbol;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal open;

    private BigDecimal adjClose;

    private int volume;

    private Date date;// TODO: need proper date validation

    public static List<StockHistoryDO> validateDateAndReturnInvalidData(List<StockHistoryDO> list) {
        List<StockHistoryDO> result = new ArrayList<>();
        for (StockHistoryDO sh : list) {
            if (sh.getDate() == null) {
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