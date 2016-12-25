
package com.petfood.mrp.exception;

import org.springframework.dao.DataAccessException;

public class SpiderSolrException extends DataAccessException {

    public SpiderSolrException(String msg) {
        super(msg);
    }
    
    public SpiderSolrException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public SpiderSolrException(Throwable cause) {
        super("error occurred while accessing Solr", cause);
    }

}
