package com.jsm.exptool.ui.experiments.create.configure;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.FrequencyConfigurationVO;
import com.jsm.exptool.providers.TimeDisplayStringProvider;


public class ExperimentCreateConfigureDataViewHolder extends BaseRecyclerViewHolder<FrequencyConfigurationVO<SensorConfig>> {

    private final TextView titleTV;
    private final TextView frequencyTV;

    protected ExperimentCreateConfigureDataViewHolder(View v) {
        super(v);

        this.titleTV = v.findViewById(R.id.sensorTitleTV);
        this.frequencyTV = v.findViewById(R.id.frequencyTV);
    }


    @Override
    public void fillViewHolder(FrequencyConfigurationVO<SensorConfig> element) {
        titleTV.setText(itemView.getContext().getString(element.getRepeatableElement().getNameStringResource()));
        frequencyTV.setText(element.isDefaultConfigurationEnabled()? itemView.getContext().getString(R.string.global_frequency_literal): TimeDisplayStringProvider.millisecondsToStringBestDisplay( element.getRepeatableElement().getInterval()));
    }
}
