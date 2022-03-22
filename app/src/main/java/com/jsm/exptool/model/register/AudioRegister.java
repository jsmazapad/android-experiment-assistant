package com.jsm.exptool.model.register;

import android.os.Parcel;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = AudioRegister.TABLE_NAME, inheritSuperIndices = true)
public class AudioRegister extends MediaRegister{

    /** The name of the table. */
    public static final String TABLE_NAME = "audioRegisters";

    @Ignore
    public AudioRegister(String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        super(experimentId, date,fileName, fileDirectory, dataRemoteSynced, fileRemoteSynced);
    }

    public AudioRegister(long internalId, String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        super(internalId, experimentId, date, fileName, fileDirectory, dataRemoteSynced, fileRemoteSynced);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
    }

    protected AudioRegister(Parcel in) {
        super(in);
    }

    public static final Creator<AudioRegister> CREATOR = new Creator<AudioRegister>() {
        @Override
        public AudioRegister createFromParcel(Parcel source) {
            return new AudioRegister(source);
        }

        @Override
        public AudioRegister[] newArray(int size) {
            return new AudioRegister[size];
        }
    };
}
