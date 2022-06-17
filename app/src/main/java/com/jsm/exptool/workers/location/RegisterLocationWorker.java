package com.jsm.exptool.workers.location;

import static com.jsm.exptool.config.SensorConfigConstants.TYPE_GPS;
import static com.jsm.exptool.config.SensorConfigConstants.TYPE_GPS_SENSOR_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.ACCURACY;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.ALTITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LATITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LONGITUDE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.R;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.repositories.SensorRepository;

import java.util.Date;

public class RegisterLocationWorker extends Worker {
    public RegisterLocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        double latitude = getInputData().getDouble(LATITUDE, 0);
        double longitude = getInputData().getDouble(LONGITUDE, 0);
        double altitude = getInputData().getDouble(ALTITUDE, 0);
        float accuracy = getInputData().getFloat(ACCURACY, 0);

        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(DATE_TIMESTAMP, -1);
        if (experimentId == -1 || dateTimestamp == -1) {
            return Result.failure();
        }
        //Almacenamos los registros de ubicación como registros de sensor para que la extracción de datos sea homogenea
        SensorRegister sensorRegister = new SensorRegister(experimentId,  new Date(dateTimestamp), false,  latitude, LATITUDE,
                longitude,LONGITUDE, altitude, ALTITUDE, TYPE_GPS_SENSOR_NAME, TYPE_GPS, R.string.location, accuracy);

        long insertedRowId = SensorRepository.registerSensorCapture(sensorRegister);
        Log.d("LOCATION_REGISTER", String.format("insertado con id %d", insertedRowId));

        return Result.success();
    }
}
