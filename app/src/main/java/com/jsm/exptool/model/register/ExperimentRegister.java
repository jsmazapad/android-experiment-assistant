package com.jsm.exptool.model.register;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class ExperimentRegister implements Parcelable {
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    protected long internalId;
    protected long experimentId;
    protected Date date;
    protected boolean dataRemoteSynced;

    public ExperimentRegister(long internalId,  long experimentId, Date date, boolean dataRemoteSynced) {
        this.internalId = internalId;
        this.dataRemoteSynced = dataRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public ExperimentRegister(long experimentId, Date date, boolean dataRemoteSynced) {
        this.dataRemoteSynced = dataRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public ExperimentRegister() {

    }


    public long getInternalId() {
        return internalId;
    }


    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }


    public long getExperimentId() {
        return experimentId;
    }


    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public boolean isDataRemoteSynced() {
        return dataRemoteSynced;
    }


    public void setDataRemoteSynced(boolean dataRemoteSynced) {
        this.dataRemoteSynced = dataRemoteSynced;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.internalId);
        dest.writeLong(this.experimentId);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeByte(this.dataRemoteSynced ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.internalId = source.readLong();
        this.experimentId = source.readLong();
        long tmpDate = source.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.dataRemoteSynced = source.readByte() != 0;
    }

    protected ExperimentRegister(Parcel in) {
       this.readFromParcel(in);
    }

}
