package com.petfood.mrp.model;

public class Supplier extends BaseObject {

    private String supp_code;
    private String supp_name;

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

}
