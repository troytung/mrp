package com.petfood.mrp.model;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseObject {

    private String create_by;
    private Timestamp create_dt;
    private String modify_by;
    private Timestamp modify_dt;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Timestamp getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(Timestamp create_dt) {
        this.create_dt = create_dt;
    }

    public String getModify_by() {
        return modify_by;
    }

    public void setModify_by(String modify_by) {
        this.modify_by = modify_by;
    }

    public Timestamp getModify_dt() {
        return modify_dt;
    }

    public void setModify_dt(Timestamp modify_dt) {
        this.modify_dt = modify_dt;
    }

}
