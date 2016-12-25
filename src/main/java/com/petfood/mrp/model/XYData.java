
package com.petfood.mrp.model;

public class XYData<X, Y extends Number> {

    private final X x;
    private final Y y;
    
    public XYData(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    
}
