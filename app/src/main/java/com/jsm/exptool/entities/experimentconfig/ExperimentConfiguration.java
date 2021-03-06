package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;

public class ExperimentConfiguration implements Parcelable {

    @Embedded(prefix = "camera_config_") private CameraConfig cameraConfig;
    @Embedded(prefix = "audio_config_") private AudioConfig audioConfig;
    @Embedded(prefix = "location_config_") private LocationConfig locationConfig;
    @Embedded(prefix = "sensor_config_") private SensorsGlobalConfig sensorConfig;
    @Embedded(prefix = "remote_sync_config_") private RepeatableElementConfig remoteSyncConfig;
    private List<String> quickComments = new ArrayList<>();
    private long quickCommentsCollectionUsedId;


    public ExperimentConfiguration(CameraConfig cameraConfig, AudioConfig audioConfig, LocationConfig locationConfig, SensorsGlobalConfig sensorConfig, RepeatableElementConfig remoteSyncConfig, List<String> quickComments, long quickCommentsCollectionUsedId) {
        this.cameraConfig = cameraConfig;
        this.audioConfig = audioConfig;
        this.locationConfig = locationConfig != null ? locationConfig : this.locationConfig;
        this.sensorConfig = sensorConfig;
        this.remoteSyncConfig = remoteSyncConfig;
        this.quickComments = quickComments;
        this.quickCommentsCollectionUsedId = quickCommentsCollectionUsedId;
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

    public LocationConfig getLocationConfig() {
        return locationConfig;
    }

    public void setLocationConfig(LocationConfig locationConfig) {
        this.locationConfig = locationConfig;
    }

    public SensorsGlobalConfig getSensorConfig() {
        return sensorConfig;
    }

    public void setSensorConfig(SensorsGlobalConfig sensorConfig) {
        this.sensorConfig = sensorConfig;
    }

    public RepeatableElementConfig getRemoteSyncConfig() {
        return remoteSyncConfig;
    }

    public void setRemoteSyncConfig(RepeatableElementConfig remoteSyncConfig) {
        this.remoteSyncConfig = remoteSyncConfig;
    }

    public List<String> getQuickComments() {
        return quickComments;
    }

    public long getQuickCommentsCollectionUsedId() {
        return quickCommentsCollectionUsedId;
    }

    public void setQuickCommentsCollectionUsedId(long quickCommentsCollectionUsedId) {
        this.quickCommentsCollectionUsedId = quickCommentsCollectionUsedId;
    }

    public void setQuickComments(List<String> quickComments) {
        this.quickComments = quickComments;
    }

    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene c??mara asociada
     * @return
     */
    public boolean isCameraEnabled() {
        return cameraConfig != null;
    }

    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene image embedding asociado
     * @return
     */
    public boolean isEmbeddingEnabled() {
        return cameraConfig != null && cameraConfig.getEmbeddingAlgorithm() != null;
    }

    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene audio asociado
     * @return
     */
    public boolean isAudioEnabled() {
        return audioConfig != null;
    }
    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene lectura de sensores asociada
     * @return
     */
    public boolean isSensorEnabled() {
        return sensorConfig != null && sensorConfig.getSensors() != null && sensorConfig.getSensors().size() > 0;
    }

    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene ubicaci??n asociada
     * @return
     */
    public boolean isLocationEnabled() {
        return locationConfig != null;
    }

    /**
     * M??todo auxiliar para saber si la configuraci??n del experimento tiene sincronizaci??n remota asociada
     * @return
     */
    public boolean isRemoteSyncEnabled() {
        return remoteSyncConfig != null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cameraConfig, flags);
        dest.writeParcelable(this.audioConfig, flags);
        dest.writeParcelable(this.locationConfig, flags);
        dest.writeParcelable(this.sensorConfig, flags);
        dest.writeParcelable(this.remoteSyncConfig, flags);
        dest.writeStringList(this.quickComments);
        dest.writeLong(this.quickCommentsCollectionUsedId);
    }

    public void readFromParcel(Parcel source) {
        this.cameraConfig = source.readParcelable(CameraConfig.class.getClassLoader());
        this.audioConfig = source.readParcelable(AudioConfig.class.getClassLoader());
        this.locationConfig = source.readParcelable(LocationConfig.class.getClassLoader());
        this.sensorConfig = source.readParcelable(SensorsGlobalConfig.class.getClassLoader());
        this.remoteSyncConfig = source.readParcelable(RepeatableElementConfig.class.getClassLoader());
        this.quickComments = source.createStringArrayList();
        this.quickCommentsCollectionUsedId = source.readLong();
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
