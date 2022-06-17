package com.jsm.exptool.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

public class AudioRecordingOption implements Parcelable {
    String displayName;
    @StringRes int descriptionTranslatableRes;
    private int audioSource;
    private int outputFormat;
    private int audioEncoder;
    private String fileExtension;
    int selectedEncodingBitRate;
    private List<Integer> encodingBitRatesOptions;

    public AudioRecordingOption(String displayName, @StringRes int descriptionTranslatableRes, int audioSource, int outputFormat, int audioEncoder, String fileExtension, int selectedEncodingBitRate, List<Integer> encodingBitRatesOptions) {
        this.displayName = displayName;
        this.descriptionTranslatableRes = descriptionTranslatableRes;
        this.audioSource = audioSource;
        this.outputFormat = outputFormat;
        this.audioEncoder = audioEncoder;
        this.fileExtension = fileExtension;
        this.selectedEncodingBitRate = selectedEncodingBitRate;
        this.encodingBitRatesOptions = encodingBitRatesOptions;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public @StringRes int getDescriptionTranslatableRes() {
        return descriptionTranslatableRes;
    }

    public void setDescriptionTranslatableRes(@StringRes int descriptionTranslatableRes) {
        this.descriptionTranslatableRes = descriptionTranslatableRes;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    public int getAudioEncoder() {
        return audioEncoder;
    }

    public void setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getSelectedEncodingBitRate() {
        return selectedEncodingBitRate;
    }

    public void setSelectedEncodingBitRate(int selectedEncodingBitRate) {
        this.selectedEncodingBitRate = selectedEncodingBitRate;
    }

    public List<Integer> getEncodingBitRatesOptions() {
        return encodingBitRatesOptions;
    }

    public void setEncodingBitRatesOptions(List<Integer> encodingBitRatesOptions) {
        this.encodingBitRatesOptions = encodingBitRatesOptions;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeInt(this.descriptionTranslatableRes);
        dest.writeInt(this.audioSource);
        dest.writeInt(this.outputFormat);
        dest.writeInt(this.audioEncoder);
        dest.writeString(this.fileExtension);
        dest.writeInt(this.selectedEncodingBitRate);
        dest.writeList(this.encodingBitRatesOptions);
    }

    public void readFromParcel(Parcel source) {
        this.displayName = source.readString();
        this.descriptionTranslatableRes = source.readInt();
        this.audioSource = source.readInt();
        this.outputFormat = source.readInt();
        this.audioEncoder = source.readInt();
        this.fileExtension = source.readString();
        this.selectedEncodingBitRate = source.readInt();
        this.encodingBitRatesOptions = new ArrayList<Integer>();
        source.readList(this.encodingBitRatesOptions, Integer.class.getClassLoader());
    }

    protected AudioRecordingOption(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<AudioRecordingOption> CREATOR = new Creator<AudioRecordingOption>() {
        @Override
        public AudioRecordingOption createFromParcel(Parcel source) {
            return new AudioRecordingOption(source);
        }

        @Override
        public AudioRecordingOption[] newArray(int size) {
            return new AudioRecordingOption[size];
        }
    };
}