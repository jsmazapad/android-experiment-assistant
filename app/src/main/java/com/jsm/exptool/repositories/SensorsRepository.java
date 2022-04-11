package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SensorsRepository {

    public static List<SensorConfig> getSensors(){
        return SensorHandler.getInstance().getSensors();
    }

    public static long registerSensorCapture(SensorConfig sensor, String sensorName, long experimentId, Date date){
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

    public static void getRegistersByTypeAndExperimentIdAsExperimentRegister(int type, long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData){
        responseLiveData.setValue(new ListResponse<>(new ArrayList<ExperimentRegister>(){{addAll(DBHelper.getSensorRegistersByTypeAndExperimentId(type, experimentId));}}));
    }

    public static void getRegistersByTypeAndExperimentId(int type, long experimentId, MutableLiveData<ListResponse<SensorRegister>> responseLiveData){
        responseLiveData.setValue(new ListResponse<>(DBHelper.getSensorRegistersByTypeAndExperimentId(type, experimentId)));

    }

    public static int countRegistersByExperimentId(long experimentId){
        return DBHelper.getSensorRegistersByExperimentId(experimentId).size();
    }



}
