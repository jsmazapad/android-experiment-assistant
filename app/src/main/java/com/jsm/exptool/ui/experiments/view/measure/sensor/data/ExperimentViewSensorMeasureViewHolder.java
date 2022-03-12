package com.jsm.exptool.ui.experiments.view.measure.sensor.data;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.core.utils.DateUtils;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.providers.DateProvider;

public class ExperimentViewSensorMeasureViewHolder extends BaseRecyclerViewHolder<SensorRegister> {

    TextView sensorTimeTV, sensorValueTV;

    protected ExperimentViewSensorMeasureViewHolder(View v) {
        super(v);
        this.sensorTimeTV = v.findViewById(R.id.sensorTimeTV);
        this.sensorValueTV = v.findViewById(R.id.sensorValueTV);
    }

    @Override
    public void fillViewHolder(SensorRegister element) {
        sensorTimeTV.setText(DateProvider.dateToDisplayStringWithTime(element.getDate()));
        sensorValueTV.setText(element.getDisplayString());


    }
}
