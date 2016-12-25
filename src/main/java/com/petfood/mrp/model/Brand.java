package com.petfood.mrp.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Brand {
    IPHONE, SAMSUNG, HTC, SONY, LG;
    
    private static final Logger log = LoggerFactory.getLogger(Brand.class);
    
    public static Brand safeConvert(String brand) {
        
        if(StringUtils.isNotBlank(brand)) {
            try {
                return Enum.valueOf(Brand.class, brand.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                log.debug("'{}' is not a legal value of Brand", brand);
                return null;
            }
        }
        else {
            return null;
        }
    }
}
