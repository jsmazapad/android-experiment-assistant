package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SensorRepository {

    public static List<SensorConfig> getSensors(){
        return SensorHandler.getInstance().getSensors();
    }

    public static long registerSensorCapture(SensorConfig sensor, String sensorName, long experimentId, Date date){
        String [] keysToInsert = new String[]{"","",""};
        float [] valuesToInsert = new float[]{0f,0f,0f};

        String [] keys = sensor.getSensorReader().getMeasure().keySet().toArray(new String[0]);
        Float [] values = sensor.getSensorReader().getMeasure().values().toArray(new Float[0]);


        for(int i=0; i<keys.length; i++)
        {
            keysToInsert[i] = keys[i];
            valuesToInsert[i] = values[i];
        }

        SensorRegister sensorRegister = new SensorRegister(experimentId, date, false, valuesToInsert[0], keysToInsert[0], valuesToInsert[1],
                keysToInsert[1], valuesToInsert[2], keysToInsert[2], sensorName, sensor.getSensorReader().getSensorType(), sensor.getNameStringResource(), sensor.getSensorReader().getAccuracy());
        return DBHelper.insertSensorRegister(sensorRegister);
    }

    public static long registerSensorCapture(SensorRegister sensorRegister){
        return DBHelper.insertSensorRegister(sensorRegister);
    }

    public static long updateSensorRegister(SensorRegister register) {
        return DBHelper.updateSensorRegister(register);
    }

    public static List<SensorRegister> getSynchronouslyPendingSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingSyncSensorRegistersByExperimentId(experimentId);
    }

    public static SensorRegister getSynchronouslyRegisterById(long registerId) {
        return DBHelper.getSensorRegisterById(registerId);
    }

    public static void getRegistersByTypeAndExperimentIdAsExperimentRegister(int type, long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute( () -> responseLiveData.postValue(new ListResponse<>(new ArrayList<ExperimentRegister>(DBHelper.getSensorRegistersByTypeAndExperimentId(type, experimentId)))));
    }

    public static void getRegistersByTypeAndExperimentId(int type, long experimentId, MutableLiveData<ListResponse<SensorRegister>> responseLiveData){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute( () -> responseLiveData.postValue(new ListResponse<>(DBHelper.getSensorRegistersByTypeAndExperimentId(type, experimentId))));

    }

    public static void countRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute( () -> countResponse.postValue(DBHelper.countSensorRegistersByExperimentId(experimentId)));
    }

    public static void countPendingSyncRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingSyncSensorRegistersByExperimentId(experimentId)));
    }



}
