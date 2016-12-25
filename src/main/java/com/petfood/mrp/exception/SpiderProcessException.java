
package com.petfood.mrp.exception;

public class SpiderProcessException extends RuntimeException {
    
    public SpiderProcessException(String msg) {
        super(msg);
    }
    
    public SpiderProcessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public SpiderProcessException(Throwable cause) {
        super(cause);
    }
}
