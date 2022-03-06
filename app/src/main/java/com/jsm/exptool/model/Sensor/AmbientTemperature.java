package com.jsm.exptool.model.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


import com.jsm.exptool.App;
import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Measure;
import com.jsm.exptool.model.MySensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class AmbientTemperature extends MySensor implements SensorEventListener {
    private SensorManager sensorManager = SensorHandler.getInstance().getSensorManager();
    private Sensor sensor = this.sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE);
    public float degree;

    public AmbientTemperature() {
        super(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE, R.string.ambient_temperature, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS);

        if (sensor.getMinDelay()/1000 > FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS){
            AmbientTemperature.this.intervalMin = sensor.getMinDelay()/1000;
            AmbientTemperature.this.interval = sensor.getMinDelay()/1000;
        }
    }

    public void updateGUI(){
        this.myHandler.post(new Runnable() {
            public void run() {
                AmbientTemperature.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (AmbientTemperature.this.isRecording) {
                    Date date = new Date();

                    ArrayList<Float> values = new ArrayList<Float>();
                    values.add(degree);

                    AmbientTemperature.this.data.add(new Measure(date, type, values));
                    if (BuildConfig.DEBUG)
                        Log.d("Sensor", App.getAppContext().getString(AmbientTemperature.this.getNameStringResource()) + ": " + values.toString() );

                    updateGUI();
                }else{
                    this.cancel();
                }
            }
        }, 0, (long) this.interval);
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
        if (isActive) {
            this.sensorManager.registerListener(this, sensor, 0);
        } else {
            this.sensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == type) {
            this.degree = event.values[0];
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
