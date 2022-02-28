package com.jsm.exptool.model.experimentconfig;


import android.os.Parcel;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.model.AudioRecordingOption;

public class AudioConfig extends RepeatableElement{

    @Embedded private AudioRecordingOption recordingOption;

    public AudioConfig(int interval, int intervalMin, int nameStringResource,AudioRecordingOption recordingOption) {
        super(interval, intervalMin, nameStringResource);
        this.recordingOption = recordingOption;
    }
    @Ignore
    public AudioConfig(){

    }

    public AudioRecordingOption getRecordingOption() {
        return recordingOption;
    }

    public void setRecordingOption(AudioRecordingOption recordingOption) {
        this.recordingOption = recordingOption;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.recordingOption, flags);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.recordingOption = source.readParcelable(AudioRecordingOption.class.getClassLoader());
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
