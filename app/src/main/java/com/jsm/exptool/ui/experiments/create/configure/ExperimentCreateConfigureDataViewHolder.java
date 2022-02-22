package com.jsm.exptool.ui.experiments.create.configure;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.SensorConfigurationVO;


public class ExperimentCreateConfigureDataViewHolder extends BaseRecyclerViewHolder<SensorConfigurationVO> {

    private final TextView titleTV;
    private final TextView frequencyTV;



    private final SelectFreqForSensorActionListener<MySensor> listener;
    protected ExperimentCreateConfigureDataViewHolder(View v, SelectFreqForSensorActionListener<MySensor> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.sensorTitleTV);
        this.frequencyTV = v.findViewById(R.id.frequencyTV);
        this.listener = listener;
    }


    @Override
    public void fillViewHolder(SensorConfigurationVO element) {
        titleTV.setText(itemView.getContext().getString(element.getSensor().getRName()));
        frequencyTV.setText(element.isDefaultConfigurationEnabled()? "Global":String.format("%d ms", element.getSensor().getInterval()));



    }
}
