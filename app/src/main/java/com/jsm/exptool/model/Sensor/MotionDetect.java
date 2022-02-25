package com.jsm.exptool.model.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;

import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.Measure;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.App;
import com.jsm.exptool.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class MotionDetect extends MySensor {
    private SensorManager sensorManager = (SensorManager) App.getSensorManager();
    private Sensor sensor = this.sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT);
    public final TriggerEventListener mListener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            Date date = new Date();
            ArrayList<Float> values = new ArrayList<Float>();
            values.add(1f);
            MotionDetect.this.data.add(new Measure(date, type, values));

            Log.d("Sensor", App.getAppContext().getString(MotionDetect.this.getNameStringResource()) + ": " + values.toString());
            sensorManager.requestTriggerSensor(mListener , sensor);
        }
    };

    public MotionDetect() {
        super(SensorConfigConstants.TYPE_MOTION_DETECT, R.string.motion_detect,  SensorConfigConstants.MIN_INTERVAL_MILLIS);
            MotionDetect.this.intervalMin = 0;
            MotionDetect.this.interval = 0;
    }

    public void updateGUI(){
        this.myHandler.post(new Runnable() {
            public void run() {
                MotionDetect.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (MotionDetect.this.isRecording) {
                        updateGUI();
                }else{
                    this.cancel();
                }
            }
        }, 0, (long) 1000);
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
        if (isActive) {
            Date date = new Date();
            ArrayList<Float> values = new ArrayList<Float>();
            values.add(0f);

            MotionDetect.this.data.add(new Measure(date, type, values));
            sensorManager.requestTriggerSensor(mListener , sensor);
        } else {
            sensorManager.cancelTriggerSensor(mListener , sensor);
        }
    }
}
