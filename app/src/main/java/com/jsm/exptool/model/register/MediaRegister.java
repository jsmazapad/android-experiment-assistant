package com.jsm.exptool.model.register;

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

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public boolean isFileRemoteSynced() {
        return fileRemoteSynced;
    }

    public void setFileRemoteSynced(boolean fileRemoteSynced) {
        this.fileRemoteSynced = fileRemoteSynced;
    }

}
