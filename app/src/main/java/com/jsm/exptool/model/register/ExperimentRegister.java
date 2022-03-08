package com.jsm.exptool.model.register;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class ExperimentRegister {
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

}
