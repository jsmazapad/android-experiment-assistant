package com.jsm.exptool.model.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Measure;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.App;
import com.jsm.exptool.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class RelativeHumidity extends MySensor implements SensorEventListener {
    private SensorManager sensorManager = SensorHandler.getInstance().getSensorManager();
    private Sensor sensor = this.sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY);
    public float humidity;

    public RelativeHumidity() {
        super(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY, R.string.relative_humidity,  FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS);

        if (sensor.getMinDelay()/1000 > FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS){
            RelativeHumidity.this.intervalMin = sensor.getMinDelay()/1000;
            RelativeHumidity.this.interval = sensor.getMinDelay()/1000;
        }
    }

    public void updateGUI(){
        this.myHandler.post(new Runnable() {
            public void run() {
                RelativeHumidity.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (RelativeHumidity.this.isRecording) {
                    Date date = new Date();

                    ArrayList<Float> values = new ArrayList<Float>();
                    values.add(humidity);

                    RelativeHumidity.this.data.add(new Measure(date, type, values));
                    if (BuildConfig.DEBUG)
                        Log.d("Sensor", App.getAppContext().getString(RelativeHumidity.this.getNameStringResource()) + ": " + values.toString() );

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
            this.humidity = event.values[0];
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
