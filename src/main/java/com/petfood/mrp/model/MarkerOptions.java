package com.petfood.mrp.model;

public class MarkerOptions {

    private static final String MARKER_FORMAT = "{markerOptions: { style:{0}, size:{1} }}";
    private String style;
    private int size;

    public MarkerOptions(String style, int size) {
        this.style = style;
        this.size = size;
    }

    public String getFormattedMarker() {
        // return MessageFormat.format(MARKER_FORMAT, this.style, this.size);
        return "{markerOptions: { style:'" + this.style + "', size:" + this.size + " }}";
    }

}
