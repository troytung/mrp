
package com.petfood.mrp.util;

import java.math.BigInteger;
import java.util.Random;

public final class RandomUtil {

    private static final Random RANDOM = new Random();
    
    private RandomUtil() {}
    
    public static String generate() {
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }
    
}
