
package com.petfood.mrp.web.action;

import java.util.Locale;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.opensymphony.xwork2.ActionContext;
import com.petfood.mrp.web.model.Login;

public abstract class AbstractAction {

    protected static final String SUCCESS = "success";
    protected static final int ROWS_PER_PAGE = 20;
    protected static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").withLocale(Locale.US);
    
    private Login login;
    
    public abstract String execute();
    
    public Login getLogin() {
        return login;
    }
    
    public void setLogin(Login login) {
        this.login = login;
    }
    
    public String getActionName() {
        return ActionContext.getContext().getName();
    }
        
    private Map<String, Object> getSession() {
        return ActionContext.getContext().getSession();
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T get(String key) {
        return (T) getSession().get(key);
    }
    
    protected void put(String key, Object obj) {
        getSession().put(key, obj);
    }
}
