package com.jsm.exptool.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jsm.exptool.libs.camera.CameraProvider;

public class ExperimentConfiguration implements Parcelable {
    private boolean cameraEnabled;
    private boolean audioEnabled;
    private boolean sensorsEnabled;
    private CameraProvider.FlashModes flashMode;

    public ExperimentConfiguration(boolean cameraEnabled, boolean audioEnabled, boolean sensorsEnabled, CameraProvider.FlashModes flashMode) {
        this.cameraEnabled = cameraEnabled;
        this.audioEnabled = audioEnabled;
        this.sensorsEnabled = sensorsEnabled;
        this.flashMode = flashMode;
    }

    public boolean isCameraEnabled() {
        return cameraEnabled;
    }

    public void setCameraEnabled(boolean cameraEnabled) {
        this.cameraEnabled = cameraEnabled;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public boolean isSensorsEnabled() {
        return sensorsEnabled;
    }

    public void setSensorsEnabled(boolean sensorsEnabled) {
        this.sensorsEnabled = sensorsEnabled;
    }

    public CameraProvider.FlashModes getFlashMode() {
        return flashMode;
    }

    public void setFlashMode(CameraProvider.FlashModes flashMode) {
        this.flashMode = flashMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.cameraEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.audioEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.sensorsEnabled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.flashMode == null ? -1 : this.flashMode.ordinal());
    }

    public void readFromParcel(Parcel source) {
        this.cameraEnabled = source.readByte() != 0;
        this.audioEnabled = source.readByte() != 0;
        this.sensorsEnabled = source.readByte() != 0;
        int tmpFlashMode = source.readInt();
        this.flashMode = tmpFlashMode == -1 ? null : CameraProvider.FlashModes.values()[tmpFlashMode];
    }

    public ExperimentConfiguration() {
    }

    protected ExperimentConfiguration(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator<ExperimentConfiguration> CREATOR = new Parcelable.Creator<ExperimentConfiguration>() {
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
