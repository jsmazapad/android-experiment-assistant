package com.jsm.exptool.ui.experiments.create.configure;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.providers.TimeDisplayStringProvider;


public class ExperimentCreateConfigureDataViewHolder extends BaseRecyclerViewHolder<SensorConfig> {

    private final TextView titleTV, frequencyTV, minFreqTV;

    protected ExperimentCreateConfigureDataViewHolder(View v) {
        super(v);

        this.titleTV = v.findViewById(R.id.sensorTitleTV);
        this.frequencyTV = v.findViewById(R.id.frequencyTV);
        this.minFreqTV = v.findViewById(R.id.minFreqTV);

    }


    @Override
    public void fillViewHolder(SensorConfig element) {
        titleTV.setText(itemView.getContext().getString(element.getNameStringResource()));
        minFreqTV.setText(String.format(itemView.getContext().getString(R.string.min_freq_placeholder),TimeDisplayStringProvider.millisecondsToStringBestDisplay( element.getSensorSystemMinDelay())));
        String frequencyValue = "";
        if(element.isDefaultConfigurationEnabled()){
            frequencyValue = itemView.getContext().getString(R.string.global_frequency_literal);
        }else{
            frequencyValue =  TimeDisplayStringProvider.millisecondsToStringBestDisplay( element.getInterval());
        }
        frequencyTV.setText(frequencyValue);
    }
}
