package com.jsm.exptool.ui.experiments.create.configure;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.MySensor;


public class ExperimentCreateConfigureDataViewHolder extends BaseRecyclerViewHolder<MySensor> {

    private final TextView titleTV;
    private final SwitchMaterial switchSensorGlobalFrequency;
    private final Slider sliderFrequency;

    private final SelectFreqForSensorActionListener<MySensor> listener;
    protected ExperimentCreateConfigureDataViewHolder(View v, SelectFreqForSensorActionListener<MySensor> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.sensorTitleTV);
        this.switchSensorGlobalFrequency = v.findViewById(R.id.switchSensorGlobalFrequency);
        this.sliderFrequency = v.findViewById(R.id.sliderFrequency);
        this.listener = listener;
    }


    @Override
    public void fillViewHolder(MySensor element) {
        titleTV.setText(itemView.getContext().getString(element.getRName()));
        //deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element);}});
        switchSensorGlobalFrequency.setChecked(true);
        switchSensorGlobalFrequency.setOnCheckedChangeListener((compoundButton, b) -> {
            listener.setDefaultFreqForSensor(element, b, (int) sliderFrequency.getValue());
            sliderFrequency.setVisibility(b? View.GONE : View.VISIBLE);
        });
        sliderFrequency.clearOnChangeListeners();
        sliderFrequency.addOnChangeListener((slider, value, fromUser) -> {
            listener.setFreqForSensor(element, (int) value);
        });
        sliderFrequency.setValueFrom(listener.getSliderMinValue());
        sliderFrequency.setValueTo(listener.getSliderMaxValue());
        sliderFrequency.setValue(listener.getSliderDefaultValue());


    }
}
