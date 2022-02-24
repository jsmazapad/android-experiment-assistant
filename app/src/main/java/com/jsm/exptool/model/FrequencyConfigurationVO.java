package com.jsm.exptool.model;

public class FrequencyConfigurationVO<T extends Repeatable> {
    private T repeatableElement;
    private boolean defaultConfigurationEnabled = true;

    public FrequencyConfigurationVO(T repeatableElement, boolean defaultConfigurationEnabled) {
        this.repeatableElement = repeatableElement;
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }

    public FrequencyConfigurationVO(T repeatableElement) {
        this.repeatableElement = repeatableElement;
    }

    public T getRepeatableElement() {
        return repeatableElement;
    }

    public void setRepeatableElement(T repeatableElement) {
        this.repeatableElement = repeatableElement;
    }

    public boolean isDefaultConfigurationEnabled() {
        return defaultConfigurationEnabled;
    }

    public void setDefaultConfigurationEnabled(boolean defaultConfigurationEnabled) {
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }
}
