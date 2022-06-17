package com.jsm.exptool.entities.experimentconfig;


import android.os.Parcel;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.R;
import com.jsm.exptool.entities.AudioRecordingOption;

public class AudioConfig extends MultimediaConfig {

    @Embedded
    private AudioRecordingOption recordingOption;
    int recordingDuration;

    public AudioConfig(int interval, int intervalMin, int intervalMax, int nameStringResource,  AudioRecordingOption recordingOption, int recordingDuration) {
        super(interval, intervalMin,intervalMax, nameStringResource);
        this.recordingOption = recordingOption;
        this.recordingDuration = recordingDuration;
    }

    @Ignore
    public AudioConfig(int interval, int intervalMin, int intervalMax, AudioRecordingOption recordingOption, int recordingDuration) {
        super(interval, intervalMin,intervalMax, R.string.audio);
        this.recordingOption = recordingOption;
        this.recordingDuration = recordingDuration;
    }

    @Ignore
    public AudioConfig(int interval, int intervalMin, int intervalMax){
        super(interval, intervalMin,intervalMax, R.string.audio);
    }

//    @Ignore
//    public AudioConfig() {
//        this.nameStringResource = R.string.audio;
//    }

    public AudioRecordingOption getRecordingOption() {
        return recordingOption;
    }

    public void setRecordingOption(AudioRecordingOption recordingOption) {
        this.recordingOption = recordingOption;
    }

    public int getRecordingDuration() {
        return recordingDuration;
    }

    public void setRecordingDuration(int recordingDuration) {
        this.recordingDuration = recordingDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.recordingOption, flags);
        dest.writeInt(this.recordingDuration);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.recordingOption = source.readParcelable(AudioRecordingOption.class.getClassLoader());
        this.recordingDuration = source.readInt();
    }

    protected AudioConfig(Parcel in) {
        super(in);
        this.recordingOption = in.readParcelable(AudioRecordingOption.class.getClassLoader());
    }

    public static final Creator<AudioConfig> CREATOR = new Creator<AudioConfig>() {
        @Override
        public AudioConfig createFromParcel(Parcel source) {
            return new AudioConfig(source);
        }

        @Override
        public AudioConfig[] newArray(int size) {
            return new AudioConfig[size];
        }
    };
}
