package com.jsm.exptool.providers;

import android.content.Context;

import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.TableToCSVExporter;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportDataProvider {



    public static final List<String> ELEMENTS_TO_EXPORT = new ArrayList<String>(){{
        add(Experiment.TABLE_NAME);
        add(AudioRegister.TABLE_NAME);
        add(ImageRegister.TABLE_NAME);
        add(SensorRegister.TABLE_NAME);
        add(CommentRegister.TABLE_NAME);
    }};




    public static File exportRegisters(Experiment experiment, String tableName, Context context ){
        String keyToCompare = "experimentId";
        if(Experiment.TABLE_NAME.equals(tableName)){
            keyToCompare = Experiment.COLUMN_ID;
        }
        String where = " WHERE "+keyToCompare+" = "+ experiment.getInternalId();
        File file = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.EXPORTED_FILES), experiment.getTitle().replace(" ", "_") + "_" + tableName+ DateProvider.dateToDisplayStringWithTime(experiment.getEndDate()).replace("/", "_")+".csv");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TableToCSVExporter.exportToCsvFromTable(file, tableName, where, DBHelper.getAppDatabase());
    }


}
