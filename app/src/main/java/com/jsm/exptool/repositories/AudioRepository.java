package com.jsm.exptool.repositories;

import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.register.AudioRegister;

import java.io.File;
import java.util.Date;

public class AudioRepository {

    public static long registerAudioRecording(File imageFile, long experimentId, Date date){
        AudioRegister audioRegister = new AudioRegister(imageFile.getName(), imageFile.getParent(), false, false, experimentId, date);

        return DBHelper.insertAudioRegister(audioRegister);

    }
}
