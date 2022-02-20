package com.jsm.exptool.model.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.Measure;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.App;
import com.jsm.exptool.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class Proximity extends MySensor implements SensorEventListener {
    private SensorManager sensorManager = (SensorManager) App.getSensorManager();
    private Sensor sensor = this.sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY);
    public float proximity;

    public Proximity() {
        super(SensorConfigConstants.TYPE_PROXIMITY, R.string.proximity,  SensorConfigConstants.MIN_INTERVAL_MILLIS);

        if (sensor.getMinDelay()/1000 > SensorConfigConstants.MIN_INTERVAL_MILLIS){
            Proximity.this.intervalMin = sensor.getMinDelay()/1000;
            Proximity.this.interval = sensor.getMinDelay()/1000;
        }
    }

    public void updateGUI(){
        this.myHandler.post(new Runnable() {
            public void run() {
                Proximity.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (Proximity.this.isRecording) {
                    Date date = new Date();

                    ArrayList<Float> values = new ArrayList<Float>();
                    values.add(proximity);

                    Proximity.this.data.add(new Measure(date, type, values));
                    if (BuildConfig.DEBUG)
                        Log.d("Sensor", App.getAppContext().getString(Proximity.this.getRName()) + ": " + values.toString() );

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
            sensorManager.registerListener(this, sensor, 0);
        } else {
            sensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == type) {
            this.proximity = event.values[0];
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
