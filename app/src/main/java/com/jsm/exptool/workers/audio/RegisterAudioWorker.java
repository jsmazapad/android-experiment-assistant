package com.jsm.exptool.workers.audio;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.IMAGE_REGISTER_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.ImagesRepository;

import java.io.File;
import java.util.Date;


public class RegisterAudioWorker extends Worker {
    public RegisterAudioWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageFileName = getInputData().getString(FILE_NAME);
        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(DATE_TIMESTAMP, -1);
        if (imageFileName == null || experimentId == -1 || dateTimestamp == -1) {
            return Result.failure();
        }
        File audioFile = new File(imageFileName);
       //TODO Refactorizar para pasar un objeto limpio
        long insertedRowId = AudioRepository.registerAudioRecording(audioFile, experimentId, new Date(dateTimestamp));
        Log.d("AUDIO_REGISTER", String.format("insertado con id %d", insertedRowId));

        return Result.success();
    }
}
