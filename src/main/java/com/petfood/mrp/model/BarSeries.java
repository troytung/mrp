package com.petfood.mrp.model;

import java.math.BigDecimal;

public class BarSeries {

    private String label;
    private Long count;
    private BigDecimal percent;

    public BarSeries(String label, Long count, BigDecimal percent) {
        super();
        this.label = label;
        this.count = count;
        this.percent = percent;
    }

    public String getLabel() {
        return label;
    }

    public Long getCount() {
        return count;
    }

    public BigDecimal getPercent() {
        return percent;
    }

}
