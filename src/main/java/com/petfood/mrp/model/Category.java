
package com.petfood.mrp.model;

import org.apache.commons.lang3.StringUtils;

public class Category {

    private Long id;
    private String name;
    private String displayQuery;
    private String query;
    private String separatedQuery;
    private String color;
    private boolean email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayQuery() {
        return displayQuery;
    }

    public void setDisplayQuery(String displayQuery) {
        this.displayQuery = displayQuery;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSeparatedQuery() {
        return separatedQuery;
    }

    public void setSeparatedQuery(String separatedQuery) {
        this.separatedQuery = separatedQuery;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        
        if(StringUtils.isNotEmpty(color) && color.startsWith("#")) {
            color = color.substring(1);
        }
        this.color = color;
    }
    
}
