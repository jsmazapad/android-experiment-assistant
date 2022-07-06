package com.jsm.exptool.entities.embedding;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;
import androidx.room.Ignore;

public class EmbeddingAlgorithm implements Parcelable {
    String displayName;
    String remoteServerName;
    @Ignore
    @StringRes int descriptionTranslatableRes;
    int optimalImageWidth;
    int optimalImageHeight;
    int maxParallelRequest;


    public EmbeddingAlgorithm(String displayName, String remoteServerName,  int optimalImageWidth, int optimalImageHeight, int maxParallelRequest) {
        this.displayName = displayName;
        this.remoteServerName = remoteServerName;
        this.optimalImageWidth = optimalImageWidth;
        this.optimalImageHeight = optimalImageHeight;
        this.maxParallelRequest = maxParallelRequest;
    }
    @Ignore
    public EmbeddingAlgorithm(String displayName, String remoteServerName, @StringRes int descriptionTranslatableRes, int optimalImageWidth, int optimalImageHeight, int maxParallelRequest) {
        this.displayName = displayName;
        this.remoteServerName = remoteServerName;
        this.descriptionTranslatableRes = descriptionTranslatableRes;
        this.optimalImageWidth = optimalImageWidth;
        this.optimalImageHeight = optimalImageHeight;
        this.maxParallelRequest = maxParallelRequest;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRemoteServerName() {
        return remoteServerName;
    }

    public void setRemoteServerName(String remoteServerName) {
        this.remoteServerName = remoteServerName;
    }

    public @StringRes int getDescriptionTranslatableRes() {
        return descriptionTranslatableRes;
    }

    public void setDescriptionTranslatableRes(@StringRes int descriptionTranslatableRes) {
        this.descriptionTranslatableRes = descriptionTranslatableRes;
    }

    public int getOptimalImageWidth() {
        return optimalImageWidth;
    }

    public void setOptimalImageWidth(int optimalImageWidth) {
        this.optimalImageWidth = optimalImageWidth;
    }

    public int getOptimalImageHeight() {
        return optimalImageHeight;
    }

    public void setOptimalImageHeight(int optimalImageHeight) {
        this.optimalImageHeight = optimalImageHeight;
    }

    public int getMaxParallelRequest() {
        return maxParallelRequest;
    }

    public void setMaxParallelRequest(int maxParallelRequest) {
        this.maxParallelRequest = maxParallelRequest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.remoteServerName);
        dest.writeInt(this.descriptionTranslatableRes);
        dest.writeInt(this.optimalImageWidth);
        dest.writeInt(this.optimalImageHeight);
        dest.writeInt(this.maxParallelRequest);
    }

    public void readFromParcel(Parcel source) {
        this.displayName = source.readString();
        this.remoteServerName = source.readString();
        this.descriptionTranslatableRes = source.readInt();
        this.optimalImageWidth = source.readInt();
        this.optimalImageHeight = source.readInt();
        this.maxParallelRequest = source.readInt();
    }

    protected EmbeddingAlgorithm(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<EmbeddingAlgorithm> CREATOR = new Creator<EmbeddingAlgorithm>() {
        @Override
        public EmbeddingAlgorithm createFromParcel(Parcel source) {
            return new EmbeddingAlgorithm(source);
        }

        @Override
        public EmbeddingAlgorithm[] newArray(int size) {
            return new EmbeddingAlgorithm[size];
        }
    };

}