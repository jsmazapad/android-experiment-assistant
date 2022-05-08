package com.jsm.exptool.config.exporttocsv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVExportConfiguration;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.HashMap;
import java.util.Map;

public class ExportToCSVConfigOptions {
    public static final Map <String, TableToCSVExportConfiguration> EXPORT_TO_CSV_OPTIONS = new HashMap<String, TableToCSVExportConfiguration>(){{
        put(Experiment.TABLE_NAME, new ExperimentExportConfiguration());
        put(SensorRegister.TABLE_NAME, new SensorRegistersExportConfiguration());
        put(ImageRegister.TABLE_NAME, new ImageRegistersExportConfiguration());
        put(AudioRegister.TABLE_NAME, new AudioRegistersExportConfiguration());
        put(CommentRegister.TABLE_NAME, new CommentRegistersExportConfiguration());
    }};
}
