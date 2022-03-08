package com.jsm.exptool.repositories;

import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class SensorsRepository {

    public static List<MySensor> getSensors(){
        return SensorHandler.getInstance().getSensors();
    }

    public static long registerSensorCapture(MySensor sensor, String sensorName, long experimentId, Date date){
        String [] keysToInsert = new String[]{"","",""};
        float [] valuesToInsert = new float[]{0f,0f,0f};

        String [] keys = sensor.getMeasure().keySet().toArray(new String[0]);
        Float [] values = sensor.getMeasure().values().toArray(new Float[0]);


        for(int i=0; i<keys.length; i++)
        {
            keysToInsert[i] = keys[i];
            valuesToInsert[i] = values[i];
        }

        SensorRegister sensorRegister = new SensorRegister(experimentId, date, false, valuesToInsert[0], keysToInsert[0], valuesToInsert[1],
                keysToInsert[1], valuesToInsert[2], keysToInsert[2], sensorName, sensor.getSensorType(), sensor.getNameStringResource(), sensor.getAccuracy());
        return DBHelper.insertSensorRegister(sensorRegister);
    }

//    public static long registerSensorCapture(String[] keys, Float[] values, int accuracy, String sensorName, long experimentId, Date date){
//        String [] keysToInsert = new String[]{"","",""};
//        float [] valuesToInsert = new float[]{0f,0f,0f};
//
//        for(int i=0; i<keys.length; i++)
//        {
//            keysToInsert[i] = keys[i];
//            valuesToInsert[i] = values[i];
//        }
//
//        SensorRegister sensorRegister = new SensorRegister(experimentId, date, false, valuesToInsert[0], keysToInsert[0], valuesToInsert[1],
//                keysToInsert[1], valuesToInsert[2], keysToInsert[2], sensorName, accuracy);
//        return DBHelper.insertSensorRegister(sensorRegister);
//    }

}
