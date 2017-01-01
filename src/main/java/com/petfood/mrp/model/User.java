package com.petfood.mrp.model;

import org.joda.time.DateTime;

public class User {

    private String userCode;
    private String userName;
    private String roleCode;
    private Role role;
    private String email;
    private String salt;
    private String password;
    private String confirmPassword;
    private boolean admin;
    private boolean active;
    private boolean receiveEmail;
    private String createBy;
    private DateTime createDt;
    private String modifyBy;
    private DateTime modifyDt;

    private String userCodeErr;
    private String userNameErr;
    private String emailErr;
    private String passwordErr;
    private String confirmPasswordErr;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public DateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(DateTime createDt) {
        this.createDt = createDt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public DateTime getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(DateTime modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getUserCodeErr() {
        return userCodeErr;
    }

    public void setUserCodeErr(String userCodeErr) {
        this.userCodeErr = userCodeErr;
    }

    public String getUserNameErr() {
        return userNameErr;
    }

    public void setUserNameErr(String userNameErr) {
        this.userNameErr = userNameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }

    public String getConfirmPasswordErr() {
        return confirmPasswordErr;
    }

    public void setConfirmPasswordErr(String confirmPasswordErr) {
        this.confirmPasswordErr = confirmPasswordErr;
    }

}
