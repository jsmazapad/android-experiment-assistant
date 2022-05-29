package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AudioRepository {

    public static long registerAudioRecording(File imageFile, long experimentId, Date date) {
        AudioRegister audioRegister = new AudioRegister(imageFile.getName(), imageFile.getParent(), false, false, experimentId, date);

        return DBHelper.insertAudioRegister(audioRegister);

    }

    public static int updateAudioRegister(AudioRegister register){
        return DBHelper.updateAudioRegister(register);
    }

    public static int updateRegisterFileSyncedByRegisterId(long registerId){
        return DBHelper.updateAudioRegisterFileSyncedByRegisterId(registerId);
    }

    public static void getRegistersByExperimentIdAsExperimentRegister(long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.postValue(new ListResponse<>(new ArrayList<ExperimentRegister>() {{
            addAll(DBHelper.getAudioRegistersByExperimentId(experimentId));
        }})));

    }

    public static List<AudioRegister> getSynchronouslyPendingSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingSyncAudioRegistersByExperimentId(experimentId);
    }

    public static List<AudioRegister> getSynchronouslyPendingFileSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingFileSyncAudioRegistersByExperimentId(experimentId);
    }

    public static AudioRegister getSynchronouslyRegisterById(long registerId) {
        return DBHelper.getAudioById(registerId);
    }

    public static void getRegistersByExperimentId(long experimentId, MutableLiveData<ListResponse<AudioRegister>> responseLiveData) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.postValue(new ListResponse<>(DBHelper.getAudioRegistersByExperimentId(experimentId))));

    }

    public static void countRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countAudioRegistersByExperimentId(experimentId)));
    }

    public static void countPendingSyncRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingSyncAudioRegistersByExperimentId(experimentId)));
    }

    public static void countPendingSyncFileRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingFileSyncAudioRegistersByExperimentId(experimentId)));
    }

}
