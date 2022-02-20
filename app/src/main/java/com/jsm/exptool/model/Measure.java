package com.jsm.exptool.model;

import java.util.ArrayList;
import java.util.Date;

public class Measure {
    private int id;
    private int id_measurement;
    private Date date;
    private Integer type_sensor;
    private ArrayList <Float> values;

    public Measure(){};

    public Measure(Date date, Integer type_sensor, ArrayList <Float> values) {
        this.date = date;
        this.type_sensor = type_sensor;
        this.values = values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_measurement() {
        return id_measurement;
    }

    public void setId_measurement(int id_measurement) {
        this.id_measurement = id_measurement;
    }

    public Date getTime() {
        return date;
    }

    public void setTime(Date time) {
        this.date = time;
    }

    public Integer getType_sensor() {
        return type_sensor;
    }

    public void setType_sensor(Integer type_sensor) {
        this.type_sensor = type_sensor;
    }

    public ArrayList <Float> getValues() {
        return values;
    }

    public void setValues(ArrayList <Float> value) {
        this.values = value;
    }

    public String toStringValues(){
        String string = "";

        if (getValues().size() == 1){
            string += getValues().get(0);
        }else if(getValues().size() == 2) {
            string += getValues().get(0) + " | Lat\n" +  getValues().get(1) + " | Lon";
        }else{
            string += getValues().get(0) + " | X\n" +  getValues().get(1) + " | Y\n" + getValues().get(2) + " | Z";
        }

        return string;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", id_measurement=" + id_measurement +
                ", date='" + date + '\'' +
                ", type_sensor=" + type_sensor +
                ", values=" + values +
                '}';
    }
}
