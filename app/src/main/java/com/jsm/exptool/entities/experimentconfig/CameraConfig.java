package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.R;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.entities.embedding.EmbeddingAlgorithm;

public class CameraConfig extends MultimediaConfig{


    CameraProvider.FlashModes flashMode = CameraProvider.FlashModes.OFF;
    CameraProvider.CameraPositions cameraPosition = CameraProvider.CameraPositions.REAR;
    @Embedded EmbeddingAlgorithm embeddingAlgorithm;
    public CameraConfig(int interval, int intervalMin, int intervalMax, CameraProvider.FlashModes flashMode, CameraProvider.CameraPositions cameraPosition, EmbeddingAlgorithm embeddingAlgorithm) {
        super(interval, intervalMin, intervalMax);
        this.flashMode = flashMode;
        this.cameraPosition = cameraPosition;
        this.embeddingAlgorithm = embeddingAlgorithm;
    }

    @Ignore
    public CameraConfig( int interval, int intervalMin, int intervalMax){
        super(interval, intervalMin, intervalMax);
    }

    @Override
    public int getNameStringRes() {
        return R.string.camera;
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
        super.writeToParcel(dest, flags);
        dest.writeInt(this.flashMode == null ? -1 : this.flashMode.ordinal());
        dest.writeInt(this.cameraPosition == null ? -1 : this.cameraPosition.ordinal());
        dest.writeParcelable(this.embeddingAlgorithm, flags);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        int tmpFlashMode = source.readInt();
        this.flashMode = tmpFlashMode == -1 ? null : CameraProvider.FlashModes.values()[tmpFlashMode];
        int tmpCameraPosition = source.readInt();
        this.cameraPosition = tmpCameraPosition == -1 ? null : CameraProvider.CameraPositions.values()[tmpCameraPosition];
        this.embeddingAlgorithm = source.readParcelable(EmbeddingAlgorithm.class.getClassLoader());
    }

    protected CameraConfig(Parcel in) {
        super(in);
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
