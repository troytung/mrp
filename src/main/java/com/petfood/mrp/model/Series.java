package com.petfood.mrp.model;

import java.util.List;

import org.joda.time.DateTime;

public class Series {

    private Long category_id;
    private String label;
    private List<XYData<DateTime, Long>> data;
    private String color;
    private String markerOptions;

    public Series(Long category_id, String label, List<XYData<DateTime, Long>> data, String color, String markerOptions) {
        this.category_id = category_id;
        this.label = label;
        this.data = data;
        this.color = color;
        this.markerOptions = markerOptions;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public String getLabel() {
        return label;
    }

    public List<XYData<DateTime, Long>> getData() {
        return data;
    }

    public String getColor() {
        return color;
    }

    public String getMarkerOptions() {
        return markerOptions;
    }

}
