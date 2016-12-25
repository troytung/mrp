package com.petfood.mrp.model;

public class Material extends BaseObject {

    private String mat_code;
    private String mat_name;
    private String supp_code;
    private String unit_name;

    public String getMat_code() {
        return mat_code;
    }

    public void setMat_code(String mat_code) {
        this.mat_code = mat_code;
    }

    public String getMat_name() {
        return mat_name;
    }

    public void setMat_name(String mat_name) {
        this.mat_name = mat_name;
    }

    public String getSupp_code() {
        return supp_code;
    }

    public void setSupp_code(String supp_code) {
        this.supp_code = supp_code;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

}
