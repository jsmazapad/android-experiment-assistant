package com.jsm.exptool.repositories;

import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.RepeatableElement;

import java.util.List;

public class SensorsRepository {

    public static List<MySensor> getSensors(){
        return SensorHandler.getInstance().getSensors();
    }
}
