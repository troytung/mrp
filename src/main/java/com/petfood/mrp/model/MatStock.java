package com.petfood.mrp.model;

import java.math.BigDecimal;

public class MatStock extends BaseObject {

    private String mat_code;
    private BigDecimal amounts;
    private boolean add;
    private BigDecimal adj_amounts;

    public String getMat_code() {
        return mat_code;
    }

    public void setMat_code(String mat_code) {
        this.mat_code = mat_code;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal amounts) {
        this.amounts = amounts;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public BigDecimal getAdj_amounts() {
        return adj_amounts;
    }

    public void setAdj_amounts(BigDecimal adj_amounts) {
        this.adj_amounts = adj_amounts;
    }

}
