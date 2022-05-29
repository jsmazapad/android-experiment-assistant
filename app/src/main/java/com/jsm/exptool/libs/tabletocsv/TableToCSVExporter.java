package com.jsm.exptool.libs.tabletocsv;

import android.database.Cursor;
import android.util.Log;

import com.jsm.exptool.csv.ExperimentsDatabaseTableToCSVExportMethod;
import com.jsm.exptool.data.database.AppDatabase;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class TableToCSVExporter {

    public static File exportToCsvFromTable(File file, ExperimentsDatabaseTableToCSVExportMethod tableToCSVExportMethod, String [] filterArgs, AppDatabase db) {
        File fileToReturn = null;
        CSVWriter csvWriter = null;
        Cursor cursor = null;
        try {
            cursor = db.query(tableToCSVExportMethod.getQuery(), filterArgs);
            csvWriter = new CSVWriter(new FileWriter(file));
            tableToCSVExportMethod.writeCSVFromCursor(cursor, csvWriter);
            fileToReturn = file;

        } catch (Exception e) {
            Log.w(TableToCSVExporter.class.getSimpleName(), e.getMessage(), e);
        } finally {

            try {
                cursor.close();
                csvWriter.close();

            } catch (Exception e) {
                Log.w(TableToCSVExporter.class.getSimpleName(), e.getMessage(), e);
            }
            return fileToReturn;

        }
    }

}
