package com.jsm.exptool.providers.worksorchestrator.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.IMAGE_REGISTER_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Date;


public class RegisterImageWorker extends Worker {
    public RegisterImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        String imageFileName = getInputData().getString(FILE_NAME);
        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(DATE_TIMESTAMP, -1);
        if (imageFileName == null || experimentId == -1 || dateTimestamp == -1) {
            return ListenableWorker.Result.failure();
        }
        File imageFile = new File(imageFileName);
       //TODO Refactorizar para pasar un objeto limpio
        long insertedRowId = ImageRepository.registerImageCapture(imageFile, experimentId, new Date(dateTimestamp));
        Log.d("IMAGE_REGISTER", String.format("insertado con id %d", insertedRowId));
        Data outputData = new Data.Builder()
                .putLong(IMAGE_REGISTER_ID, insertedRowId)
                .build();
        EventBus.getDefault().post(new WorkFinishedEvent(getTags(), true, 1));
        return ListenableWorker.Result.success(outputData);
    }
}
