package com.jsm.exptool.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InputOutputExampleWorker extends Worker {
    public InputOutputExampleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String resourceUri = getInputData().getString("CONSTANTE_URI");
        Log.d("INPUT", resourceUri);
        Data outputData = new Data.Builder()
                .putString("CONSTANTE_URI", resourceUri.toString())
                .build();
        return Result.success(outputData);
    }
}
