package com.jsm.exptool.config;

import com.jsm.exptool.providers.export.csv.AudioRegistersCSVExportMethod;
import com.jsm.exptool.providers.export.csv.CommentRegistersCSVExportMethod;
import com.jsm.exptool.providers.export.csv.ExperimentExportCSVExportMethod;
import com.jsm.exptool.providers.export.csv.ImageRegistersCSVExportMethod;
import com.jsm.exptool.providers.export.csv.SensorRegistersCSVExportMethod;
import com.jsm.exptool.providers.export.csv.ExperimentsDatabaseTableToCSVExportMethod;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;

import java.util.HashMap;
import java.util.Map;

public class ExportToCSVConfigOptions {
    public static final Map<String, ExperimentsDatabaseTableToCSVExportMethod> EXPORT_TO_CSV_OPTIONS = initializeExportOptions();

    private static HashMap<String, ExperimentsDatabaseTableToCSVExportMethod> initializeExportOptions() {
        HashMap<String, ExperimentsDatabaseTableToCSVExportMethod> mapToReturn = new HashMap<>();
        mapToReturn.put(Experiment.TABLE_NAME, new ExperimentExportCSVExportMethod());
        mapToReturn.put(SensorRegister.TABLE_NAME, new SensorRegistersCSVExportMethod());
        mapToReturn.put(ImageRegister.TABLE_NAME, new ImageRegistersCSVExportMethod());
        mapToReturn.put(AudioRegister.TABLE_NAME, new AudioRegistersCSVExportMethod());
        mapToReturn.put(CommentRegister.TABLE_NAME, new CommentRegistersCSVExportMethod());
        return mapToReturn;
    }
}
