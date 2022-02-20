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

public class GameRotationVector extends MySensor implements SensorEventListener {
    private SensorManager sensorManager = (SensorManager) App.getSensorManager();
    private Sensor sensor = this.sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR);
    public float x;
    public float y;
    public float z;

    public GameRotationVector() {
        super(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR, R.string.game_rotation_vector, SensorConfigConstants.MIN_INTERVAL_MILLIS);

        if (sensor.getMinDelay()/1000 > SensorConfigConstants.MIN_INTERVAL_MILLIS){
            GameRotationVector.this.intervalMin = sensor.getMinDelay()/1000;
            GameRotationVector.this.interval = sensor.getMinDelay()/1000;
        }

    }

    public void updateGUI(){
        this.myHandler.post(new Runnable() {
            public void run() {
                GameRotationVector.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (GameRotationVector.this.isRecording) {
                    Date date = new Date();

                    ArrayList<Float> values = new ArrayList<Float>();
                    values.add(x);
                    values.add(y);
                    values.add(z);

                    GameRotationVector.this.data.add(new Measure(date, type, values));
                    if (BuildConfig.DEBUG)
                        Log.d("Sensor", App.getAppContext().getString(GameRotationVector.this.getRName()) + ": " + values.toString() );

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
            this.x = event.values[0];
            this.y = event.values[1];
            this.z = event.values[2];
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
