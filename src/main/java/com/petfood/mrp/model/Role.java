package com.petfood.mrp.model;

public enum Role {

    R01("廠長"), R02("副廠長"), R03("作業員組長"), R04("作業員"), R99("系統管理員");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
