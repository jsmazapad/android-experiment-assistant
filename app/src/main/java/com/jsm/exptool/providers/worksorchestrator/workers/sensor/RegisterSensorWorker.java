package com.jsm.exptool.providers.worksorchestrator.workers.sensor;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR_NAME;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.data.repositories.SensorRepository;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

public class RegisterSensorWorker extends Worker {
    public RegisterSensorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SensorConfig sensor = null;
        String sensorName = getInputData().getString(SENSOR_NAME);
        try {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
             sensor = gson.fromJson(getInputData().getString(SENSOR), SensorConfig.class);
        }catch (Exception e){
            Log.w("ERROR EN PARSEO", "ERROR EN PARSEO");
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
        }
        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(DATE_TIMESTAMP, -1);
        if (sensorName == null || experimentId == -1 || dateTimestamp == -1 || sensor == null) {
            return Result.failure();
        }
        //TODO Refactorizar para pasar un objeto limpio
        //long insertedRowId = SensorsRepository.registerSensorCapture(keys, values, experimentId, new Date(dateTimestamp));
        long insertedRowId = SensorRepository.registerSensorCapture(sensor,sensorName, experimentId, new Date(dateTimestamp));
        Log.d("SENSOR_REGISTER", String.format("insertado con id %d", insertedRowId));
        EventBus.getDefault().post(new WorkFinishedEvent(getTags(), true, 1));
        return Result.success();
    }
}
