package com.jsm.exptool.providers.worksorchestrator.workers.export;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.TABLE_NAME;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.providers.export.ExportDataProvider;
import com.jsm.exptool.data.repositories.ExperimentsRepository;

import java.io.File;


public class ExportExperimentWorker extends Worker {
    public ExportExperimentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        String tableName = getInputData().getString(TABLE_NAME);


        if(experimentId < 0 || tableName == null){
            return Result.failure();
        }

        Experiment experiment = ExperimentsRepository.getExperimentById(experimentId);

        if(experiment == null){
            return Result.failure();
        }

        File fileToReturn = ExportDataProvider.exportRegisters(experiment, tableName, getApplicationContext());

        if(fileToReturn == null){
            return Result.failure();
        }

        Data outputData = new Data.Builder()
                .putString(FILE_NAME+"_"+tableName, fileToReturn.getPath())
                .putLong(EXPERIMENT_ID, experimentId)
                .build();
        return Result.success(outputData);
    }
}
