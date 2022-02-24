package com.jsm.exptool.model;

import android.os.Parcel;

import com.jsm.exptool.R;
import com.jsm.exptool.libs.camera.CameraProvider;

public class CameraConfig implements Repeatable{

    int rName = R.string.camera;
    int interval = 0;
    int intervalMin = 0;
    CameraProvider.FlashModes flashMode = CameraProvider.FlashModes.OFF;
    CameraProvider.CameraPositions cameraPosition = CameraProvider.CameraPositions.REAR;

    public CameraConfig(int rName, int interval, int intervalMin, CameraProvider.FlashModes flashMode, CameraProvider.CameraPositions cameraPosition) {
        this.rName = rName;
        this.interval = interval;
        this.intervalMin = intervalMin;
        this.flashMode = flashMode;
        this.cameraPosition = cameraPosition;
    }

    public CameraConfig() {

    }

    @Override
    public int getRName() {
        return rName;
    }

    public void setRName(int rName) {
        this.rName = rName;
    }

    @Override
    public int getInterval() {
        return interval;
    }

    @Override
    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public int getIntervalMin() {
        return intervalMin;
    }

    @Override
    public void setIntervalMin(int intervalMin) {
        this.intervalMin = intervalMin;
    }


    public CameraProvider.FlashModes getFlashMode() {
        return flashMode;
    }

    public void setFlashMode(CameraProvider.FlashModes flashMode) {
        this.flashMode = flashMode;
    }

    public CameraProvider.CameraPositions getCameraPosition() {
        return cameraPosition;
    }

    public void setCameraPosition(CameraProvider.CameraPositions cameraPosition) {
        this.cameraPosition = cameraPosition;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rName);
        dest.writeInt(this.interval);
        dest.writeInt(this.intervalMin);
        dest.writeInt(this.flashMode == null ? -1 : this.flashMode.ordinal());
        dest.writeInt(this.cameraPosition == null ? -1 : this.cameraPosition.ordinal());
    }

    public void readFromParcel(Parcel source) {
        this.rName = source.readInt();
        this.interval = source.readInt();
        this.intervalMin = source.readInt();
        int tmpFlashMode = source.readInt();
        this.flashMode = tmpFlashMode == -1 ? null : CameraProvider.FlashModes.values()[tmpFlashMode];
        int tmpCameraPosition = source.readInt();
        this.cameraPosition = tmpCameraPosition == -1 ? null : CameraProvider.CameraPositions.values()[tmpCameraPosition];
    }

    protected CameraConfig(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<CameraConfig> CREATOR = new Creator<CameraConfig>() {
        @Override
        public CameraConfig createFromParcel(Parcel source) {
            return new CameraConfig(source);
        }

        @Override
        public CameraConfig[] newArray(int size) {
            return new CameraConfig[size];
        }
    };
}
