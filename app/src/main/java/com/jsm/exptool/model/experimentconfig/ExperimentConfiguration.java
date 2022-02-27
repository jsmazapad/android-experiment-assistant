package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Ignore;

public class ExperimentConfiguration implements Parcelable {

    //TODO Eliminar este parámetro y hacer que extienda de RepeatableElement
    //private int defaultFrequency;
    @Embedded(prefix = "camera_config_") private CameraConfig cameraConfig;
    @Embedded(prefix = "audio_config_") private AudioConfig audioConfig;
    @Embedded(prefix = "global_config_") private GlobalConfig globalConfig = new GlobalConfig();

//    @Ignore
//    public ExperimentConfiguration(CameraConfig cameraConfig, AudioConfig audioConfig, GlobalConfig globalConfig) {
//        this.cameraConfig = cameraConfig;
//        this.audioConfig = audioConfig;
//        this.globalConfig = globalConfig != null ? globalConfig : this.globalConfig;
//    }

    public ExperimentConfiguration(CameraConfig cameraConfig, AudioConfig audioConfig, GlobalConfig globalConfig /*,int defaultFrequency*/) {
        this.cameraConfig = cameraConfig;
        this.audioConfig = audioConfig;
        this.globalConfig = globalConfig != null ? globalConfig : this.globalConfig;
//        this.defaultFrequency = defaultFrequency;
    }
    @Ignore
    public ExperimentConfiguration() {

    }

    public CameraConfig getCameraConfig() {
        return cameraConfig;
    }

    public void setCameraConfig(CameraConfig cameraConfig) {
        this.cameraConfig = cameraConfig;
    }

    public AudioConfig getAudioConfig() {
        return audioConfig;
    }

    public void setAudioConfig(AudioConfig audioConfig) {
        this.audioConfig = audioConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public boolean isCameraEnabled() {
        return cameraConfig != null;
    }

    public boolean isEmbeddingEnabled() {
        return cameraConfig != null && cameraConfig.getEmbeddingAlgorithm() != null;
    }


    public boolean isAudioEnabled() {
        return audioConfig != null;
    }
//
//    public int getDefaultFrequency() {
//        return defaultFrequency;
//    }
//
//    public void setDefaultFrequency(int defaultFrequency) {
//        this.defaultFrequency = defaultFrequency;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cameraConfig, flags);
        dest.writeParcelable(this.audioConfig, flags);
        dest.writeParcelable(this.globalConfig, flags);
    }

    public void readFromParcel(Parcel source) {
        this.cameraConfig = source.readParcelable(CameraConfig.class.getClassLoader());
        this.audioConfig = source.readParcelable(AudioConfig.class.getClassLoader());
        this.globalConfig = source.readParcelable(GlobalConfig.class.getClassLoader());
    }

    protected ExperimentConfiguration(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<ExperimentConfiguration> CREATOR = new Creator<ExperimentConfiguration>() {
        @Override
        public ExperimentConfiguration createFromParcel(Parcel source) {
            return new ExperimentConfiguration(source);
        }

        @Override
        public ExperimentConfiguration[] newArray(int size) {
            return new ExperimentConfiguration[size];
        }
    };
}
