package com.jsm.exptool.providers;

import android.content.Context;
import android.util.Log;

import com.jsm.exptool.config.ExportToCSVConfigOptions;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.tabletocsv.TableToCSVExporter;
import com.jsm.exptool.entities.Experiment;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ExportDataProvider {

    public static File exportRegisters(Experiment experiment, String tableName, Context context ){

        String fileName =  FilePathsProvider.formatFileName(experiment.getTitle() + "_" + tableName+ DateProvider.dateToDisplayStringWithTime(experiment.getEndDate())+".csv");

        File file = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.EXPORTED_FILES), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.w(ExportDataProvider.class.getSimpleName(), e.getMessage(), e);
        }
        return TableToCSVExporter.exportToCsvFromTable(file, Objects.requireNonNull(ExportToCSVConfigOptions.EXPORT_TO_CSV_OPTIONS.get(tableName)), new String[]{Long.valueOf(experiment.getInternalId()).toString()}, DBHelper.getAppDatabase());
    }


}
