package com.jsm.exptool.config;

import com.jsm.exptool.csv.AudioRegistersCSVExportMethod;
import com.jsm.exptool.csv.CommentRegistersCSVExportMethod;
import com.jsm.exptool.csv.ExperimentExportCSVExportMethod;
import com.jsm.exptool.csv.ImageRegistersCSVExportMethod;
import com.jsm.exptool.csv.SensorRegistersCSVExportMethod;
import com.jsm.exptool.csv.ExperimentsDatabaseTableToCSVExportMethod;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.HashMap;
import java.util.Map;

public class ExportToCSVConfigOptions {
    public static final Map <String, ExperimentsDatabaseTableToCSVExportMethod> EXPORT_TO_CSV_OPTIONS = new HashMap<String, ExperimentsDatabaseTableToCSVExportMethod>(){{
        put(Experiment.TABLE_NAME, new ExperimentExportCSVExportMethod());
        put(SensorRegister.TABLE_NAME, new SensorRegistersCSVExportMethod());
        put(ImageRegister.TABLE_NAME, new ImageRegistersCSVExportMethod());
        put(AudioRegister.TABLE_NAME, new AudioRegistersCSVExportMethod());
        put(CommentRegister.TABLE_NAME, new CommentRegistersCSVExportMethod());
    }};
}
