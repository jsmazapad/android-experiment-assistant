package com.jsm.exptool.model;

public class SensorConfigurationVO{
    private MySensor sensor;
    private boolean defaultConfigurationEnabled = true;

    public SensorConfigurationVO(MySensor sensor, boolean defaultConfigurationEnabled) {
        this.sensor = sensor;
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }

    public SensorConfigurationVO(MySensor sensor) {
        this.sensor = sensor;
    }

    public MySensor getSensor() {
        return sensor;
    }

    public void setSensor(MySensor sensor) {
        this.sensor = sensor;
    }

    public boolean isDefaultConfigurationEnabled() {
        return defaultConfigurationEnabled;
    }

    public void setDefaultConfigurationEnabled(boolean defaultConfigurationEnabled) {
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }
}
