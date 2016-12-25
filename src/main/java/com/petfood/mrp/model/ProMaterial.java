package com.petfood.mrp.model;

import java.math.BigDecimal;

public class ProMaterial extends BaseObject {

    private String pro_code;
    private String mat_code;
    private BigDecimal amounts;
    private String supp_code;
    private String supp_name;
    private String mat_name;
    private String unit_name;

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

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

    public String getSupp_code() {
        return supp_code;
    }

    public void setSupp_code(String supp_code) {
        this.supp_code = supp_code;
    }

    public String getSupp_name() {
        return supp_name;
    }

    public void setSupp_name(String supp_name) {
        this.supp_name = supp_name;
    }

    public String getMat_name() {
        return mat_name;
    }

    public void setMat_name(String mat_name) {
        this.mat_name = mat_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

}
