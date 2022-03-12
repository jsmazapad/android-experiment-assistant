package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.ImageRegister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class AudioRepository {

    public static long registerAudioRecording(File imageFile, long experimentId, Date date){
        AudioRegister audioRegister = new AudioRegister(imageFile.getName(), imageFile.getParent(), false, false, experimentId, date);

        return DBHelper.insertAudioRegister(audioRegister);

    }

    public static void getRegistersByExperimentIdAsExperimentRegister(long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData){
        responseLiveData.setValue(new ListResponse<>(new ArrayList<ExperimentRegister>(){{addAll(DBHelper.getAudioRegistersByExperimentId(experimentId));}}));

    }
    public static void getRegistersByExperimentId(long experimentId, MutableLiveData<ListResponse<AudioRegister>> responseLiveData){
        responseLiveData.setValue(new ListResponse<>(DBHelper.getAudioRegistersByExperimentId(experimentId)));

    }
}
