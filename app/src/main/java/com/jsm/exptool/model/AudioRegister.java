package com.jsm.exptool.model;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = AudioRegister.TABLE_NAME, inheritSuperIndices = true)
public class AudioRegister extends MediaRegister{

    /** The name of the table. */
    public static final String TABLE_NAME = "audioRegisters";

    @Ignore
    public AudioRegister(String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        super(fileName, fileDirectory, dataRemoteSynced, fileRemoteSynced, experimentId, date);
    }

    public AudioRegister(long internalId, String fileName, String fileDirectory, boolean dataRemoteSynced, boolean fileRemoteSynced, long experimentId, Date date) {
        super(internalId, fileName, fileDirectory, dataRemoteSynced, fileRemoteSynced, experimentId, date);
    }


}
