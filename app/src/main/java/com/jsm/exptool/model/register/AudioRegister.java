package com.jsm.exptool.model.register;

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


}
