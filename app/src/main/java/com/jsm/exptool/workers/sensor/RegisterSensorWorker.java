package com.jsm.exptool.workers.sensor;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.MEASURE_ACCURACY;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.MEASURE_KEYS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.MEASURE_VALUES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR_NAME;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.io.File;
import java.util.Date;

public class RegisterSensorWorker extends Worker {
    public RegisterSensorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        MySensor sensor = null;
        String sensorName = getInputData().getString(SENSOR_NAME);
        try {
             sensor = new Gson().fromJson(getInputData().getString(SENSOR), MySensor.class);
        }catch (Exception e){
            Log.e("ERROR EN PARSEO", "ERROR EN PARSEO");
            e.printStackTrace();
        }
        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(DATE_TIMESTAMP, -1);
//        int accuracy = getInputData().getInt(MEASURE_ACCURACY, -1);
//        String [] keys = getInputData().getStringArray(MEASURE_KEYS);
//        float [] values = getInputData().getFloatArray(MEASURE_VALUES);
        if (sensorName == null || experimentId == -1 || dateTimestamp == -1 || sensor == null) {
            return Result.failure();
        }
        //TODO Refactorizar para pasar un objeto limpio
        //long insertedRowId = SensorsRepository.registerSensorCapture(keys, values, experimentId, new Date(dateTimestamp));
        long insertedRowId = SensorsRepository.registerSensorCapture(sensor,sensorName, experimentId, new Date(dateTimestamp));
        Log.d("SENSOR_REGISTER", String.format("insertado con id %d", insertedRowId));

        return Result.success();
    }
}
