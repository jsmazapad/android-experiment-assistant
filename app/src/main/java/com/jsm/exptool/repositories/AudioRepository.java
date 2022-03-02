package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.AudioRegister;
import com.jsm.exptool.model.ImageRegister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class AudioRepository {

    public static long registerAudioRecording(File imageFile, long experimentId, Date date){
        AudioRegister audioRegister = new AudioRegister(imageFile.getName(), imageFile.getParent(), false, false, experimentId, date);

        return DBHelper.insertAudioRegister(audioRegister);

    }
}
