package com.jsm.exptool.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class MediaRegister {
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    protected long internalId;
    protected String fileName;
    protected String fileDirectory;
    protected boolean dataRemoteSynced;
    protected boolean fileRemoteSynced;
    protected long experimentId;
    protected Date date;

    public MediaRegister(long internalId, String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        this.internalId = internalId;
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        this.dataRemoteSynced = dataRemoteSynced;
        this.fileRemoteSynced = fileRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public MediaRegister(String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        this.dataRemoteSynced = dataRemoteSynced;
        this.fileRemoteSynced = fileRemoteSynced;
        this.experimentId = experimentId;
        this.date = date;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public boolean isDataRemoteSynced() {
        return dataRemoteSynced;
    }

    public void setDataRemoteSynced(boolean dataRemoteSynced) {
        this.dataRemoteSynced = dataRemoteSynced;
    }

    public boolean isFileRemoteSynced() {
        return fileRemoteSynced;
    }

    public void setFileRemoteSynced(boolean fileRemoteSynced) {
        this.fileRemoteSynced = fileRemoteSynced;
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
}
