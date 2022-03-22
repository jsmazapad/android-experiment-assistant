package com.jsm.exptool.model.register;

import android.os.Parcel;

import java.util.Date;

public class MediaRegister extends ExperimentRegister {
    protected String fileName;
    protected String fileDirectory;
    protected boolean fileRemoteSynced;

    public MediaRegister(long internalId, long experimentId, Date date, String fileName, String fileDirectory,  boolean dataRemoteSynced, boolean fileRemoteSynced) {
        super(internalId,  experimentId, date, dataRemoteSynced );
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        this.fileRemoteSynced = fileRemoteSynced;
    }

    public MediaRegister(long experimentId, Date date, String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced) {
        super( experimentId, date, dataRemoteSynced);
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        this.fileRemoteSynced = fileRemoteSynced;
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

    public String getFullPath(){
        return this.fileDirectory + "/" +  this.fileName;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public boolean isFileRemoteSynced() {
        return fileRemoteSynced;
    }

    public void setFileRemoteSynced(boolean fileRemoteSynced) {
        this.fileRemoteSynced = fileRemoteSynced;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.fileName);
        dest.writeString(this.fileDirectory);
        dest.writeByte(this.fileRemoteSynced ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.fileName = source.readString();
        this.fileDirectory = source.readString();
        this.fileRemoteSynced = source.readByte() != 0;
    }

    protected MediaRegister(Parcel in) {
        super(in);
        this.fileName = in.readString();
        this.fileDirectory = in.readString();
        this.fileRemoteSynced = in.readByte() != 0;
    }

}
