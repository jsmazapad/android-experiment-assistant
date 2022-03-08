package com.jsm.exptool.model;

import java.util.ArrayList;
import java.util.Date;

public class Measure {
    private float value;
    private String unit;

    public Measure(){};

    public Measure( float value, String unit) {

        this.value = value;
        this.unit = unit;
    }

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
