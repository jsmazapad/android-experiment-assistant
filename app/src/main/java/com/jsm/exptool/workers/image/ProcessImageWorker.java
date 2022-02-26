package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_FILE_MANE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.libs.ImageResizer;

import java.io.File;
import java.io.IOException;

public class ProcessImageWorker extends Worker {
    public ProcessImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageFileName = getInputData().getString(IMAGE_FILE_NAME);
        String targetImageFileName = getInputData().getString(PROCESSED_IMAGE_FILE_MANE);
        if(imageFileName == null || targetImageFileName == null){
            return Result.failure();
        }
        File imageFile = new File(imageFileName);
        File targetFile = new File(targetImageFileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResizer.resizeImageFile(imageFile, targetFile, 299);
        Data outputData = new Data.Builder()
                .putString(IMAGE_FILE_NAME, targetFile.getPath())
                .build();
        return Result.success(outputData);
    }
}
