
package com.petfood.mrp.web.action;

public enum SortColumn {

    DATE("post_date"), POSITIVE("positive"), NEGATIVE("negative");
    
    private final String solrField;
    
    private SortColumn(String solrField) {
        this.solrField = solrField;
    }
    
    public String getSolrField() {
        return solrField;
    }
}
