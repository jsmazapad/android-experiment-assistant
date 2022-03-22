package com.jsm.exptool.workers.export;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.TABLE_NAME;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.libs.Zipper;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.ExportDataProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.io.File;
import java.util.ArrayList;


public class ZipExportedExperimentWorker extends Worker {
    public ZipExportedExperimentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);

        ArrayList<String> filenames = new ArrayList<>();
        for (String tableName:ExportDataProvider.ELEMENTS_TO_EXPORT) {
            String fileName = getInputData().getString(FILE_NAME+"_"+tableName);
            if(fileName == null)
                return Result.failure();
            filenames.add(fileName);
        }


        if(experimentId < 0){
            return Result.failure();
        }

        Experiment experiment = ExperimentsRepository.getExperimentById(experimentId);

        if(experiment == null){
            return Result.failure();
        }

        Zipper zipper = new Zipper();
        String [] filenamesArray = filenames.toArray(new String[0]);
        //eEusamos el nombre del primer elemento pero cambiamos la extensión
        String zipName = filenamesArray[0].replace("csv", "zip");
        zipper.zip(filenamesArray, zipName);


        Data outputData = new Data.Builder()
                .putString(FILE_NAME, zipName)
                .build();
        return Result.success(outputData);
    }
}
