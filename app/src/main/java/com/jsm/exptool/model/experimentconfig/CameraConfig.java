package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.R;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.model.embedding.EmbeddingAlgorithm;

public class CameraConfig extends MultimediaConfig{


    CameraProvider.FlashModes flashMode = CameraProvider.FlashModes.OFF;
    CameraProvider.CameraPositions cameraPosition = CameraProvider.CameraPositions.REAR;
    @Embedded EmbeddingAlgorithm embeddingAlgorithm;
    public CameraConfig(int interval, int intervalMin, int intervalMax, int nameStringResource, CameraProvider.FlashModes flashMode, CameraProvider.CameraPositions cameraPosition, EmbeddingAlgorithm embeddingAlgorithm) {
        super(interval, intervalMin, intervalMax, nameStringResource);
        this.flashMode = flashMode;
        this.cameraPosition = cameraPosition;
        this.embeddingAlgorithm = embeddingAlgorithm;
    }
    @Ignore
    public CameraConfig( int interval, int intervalMin, int intervalMax, CameraProvider.FlashModes flashMode, CameraProvider.CameraPositions cameraPosition, EmbeddingAlgorithm embeddingAlgorithm) {
        super(interval, intervalMin, intervalMax, R.string.audio);
        this.flashMode = flashMode;
        this.cameraPosition = cameraPosition;
        this.embeddingAlgorithm = embeddingAlgorithm;
    }
    @Ignore
    public CameraConfig(){
        this.nameStringResource = R.string.camera;
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

    public EmbeddingAlgorithm getEmbeddingAlgorithm() {
        return embeddingAlgorithm;
    }

    public void setEmbeddingAlgorithm(EmbeddingAlgorithm embeddingAlgorithm) {
        this.embeddingAlgorithm = embeddingAlgorithm;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.nameStringResource);
        dest.writeInt(this.interval);
        dest.writeInt(this.intervalMin);
        dest.writeInt(this.flashMode == null ? -1 : this.flashMode.ordinal());
        dest.writeInt(this.cameraPosition == null ? -1 : this.cameraPosition.ordinal());
        dest.writeParcelable(this.embeddingAlgorithm, flags);
    }

    public void readFromParcel(Parcel source) {
        this.nameStringResource = source.readInt();
        this.interval = source.readInt();
        this.intervalMin = source.readInt();
        int tmpFlashMode = source.readInt();
        this.flashMode = tmpFlashMode == -1 ? null : CameraProvider.FlashModes.values()[tmpFlashMode];
        int tmpCameraPosition = source.readInt();
        this.cameraPosition = tmpCameraPosition == -1 ? null : CameraProvider.CameraPositions.values()[tmpCameraPosition];
        this.embeddingAlgorithm = source.readParcelable(EmbeddingAlgorithm.class.getClassLoader());
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
