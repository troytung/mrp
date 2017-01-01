package com.petfood.mrp.model;

import java.sql.Date;

public class DailySchedule extends BaseObject {

    private Date schedule_dt;
    private String pro_code;
    private Integer target_amt;
    private Integer produced_amt;
    private Integer packed_amt;
    private String cus_name;
    private String pro_name;

    public Date getSchedule_dt() {
        return schedule_dt;
    }

    public void setSchedule_dt(Date schedule_dt) {
        this.schedule_dt = schedule_dt;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public Integer getTarget_amt() {
        return target_amt;
    }

    public void setTarget_amt(Integer target_amt) {
        this.target_amt = target_amt;
    }

    public Integer getProduced_amt() {
        return produced_amt;
    }

    public void setProduced_amt(Integer produced_amt) {
        this.produced_amt = produced_amt;
    }

    public Integer getPacked_amt() {
        return packed_amt;
    }

    public void setPacked_amt(Integer packed_amt) {
        this.packed_amt = packed_amt;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

}
