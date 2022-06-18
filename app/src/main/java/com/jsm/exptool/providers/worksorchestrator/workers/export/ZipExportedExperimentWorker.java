package com.jsm.exptool.providers.worksorchestrator.workers.export;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_MULTIMEDIA_PATHS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.config.ExportToCSVConfigOptions;
import com.jsm.exptool.libs.Zipper;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.data.repositories.ExperimentsRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class ZipExportedExperimentWorker extends Worker {

    public ZipExportedExperimentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        String [] experimentFilesPath = getInputData().getStringArray(EXPERIMENT_MULTIMEDIA_PATHS);

        if(experimentId < 0 || experimentFilesPath == null){
            return Result.failure();
        }

        ArrayList<String> filenames = new ArrayList<>();
        for (String tableName: ExportToCSVConfigOptions.EXPORT_TO_CSV_OPTIONS.keySet()) {
            String fileName = getInputData().getString(FILE_NAME+"_"+tableName);
            if(fileName == null)
                return Result.failure();
            filenames.add(fileName);
        }

        filenames.addAll(Arrays.asList(experimentFilesPath));


        Experiment experiment = ExperimentsRepository.getExperimentById(experimentId);

        if(experiment == null){
            return Result.failure();
        }

        Zipper zipper = new Zipper();
        String [] filenamesArray = filenames.toArray(new String[0]);
        //Reusamos el nombre del primer elemento pero cambiamos la extensión
        String zipName = filenames.get(0).substring(0,filenames.get(0).lastIndexOf("/")+1)+ FilePathsProvider.formatFileName(experiment.getTitle()+ DateProvider.dateToDisplayStringWithTime(experiment.getInitDate())+".zip");
        Log.d("ZIP", zipName);
        try {
            zipper.zip(filenamesArray, zipName);
            experiment.setExportedPending(false);
            ExperimentsRepository.updateExperiment(experiment);
        }catch (Exception e) {
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
            return Result.failure();
        }

        //Eliminamos los archivos csv cuando terminamos
        for (String fileName:filenames) {
            try {
                if (fileName.endsWith(".csv")) {
                    new File(fileName).delete();
                }
            }catch (Exception e){
                Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
            }

        }


        Data outputData = new Data.Builder()
                .putString(FILE_NAME, zipName)
                .build();
        return Result.success(outputData);
    }
}
