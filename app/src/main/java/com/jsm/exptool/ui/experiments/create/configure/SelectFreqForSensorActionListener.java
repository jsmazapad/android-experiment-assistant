package com.jsm.exptool.ui.experiments.create.configure;

public interface SelectFreqForSensorActionListener<T> {
    void setFreqForSensor(T element, int freq);
    void setDefaultFreqForSensor(T element, boolean isDefault, int freq);
    int getSliderMinValue();
    int getSliderMaxValue();
    int getSliderDefaultValue();
}
