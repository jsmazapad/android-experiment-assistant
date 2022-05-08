package com.jsm.exptool.libs.tabletocsv;

import android.database.Cursor;

import androidx.room.Database;

import com.jsm.exptool.data.database.AppDatabase;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TableToCSVExporter {

    public static File exportToCsvFromTable(File file, TableToCSVExportConfiguration configuration, String filter, AppDatabase db) {
        File fileToReturn = null;
        CSVWriter csvWrite = null;
        Cursor cursor = null;
        try {
            cursor = db.query("SELECT * FROM " + configuration.getTableName() + filter, null);
            csvWrite = new CSVWriter(new FileWriter(file));
            ArrayList<String> columnsToInclude = new ArrayList<>(cursor.getColumnCount() - configuration.getExcludedFields().size());
            ArrayList<Integer> indexToIterate = new ArrayList<>(columnsToInclude.size());
            //Extraemos las columnas que realmente obtendremos
            // y los índices de las mismas para poder obtener los valores posteriormente
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                if (!configuration.getExcludedFields().contains(cursor.getColumnName(i))) {
                    columnsToInclude.add(columnName);
                    indexToIterate.add(i);
                }
            }

            csvWrite.writeNext(columnsToInclude.toArray(new String[columnsToInclude.size()]));
            while (cursor.moveToNext()) {
                String arrStr[] = new String[indexToIterate.size()];
                //Recorremos sólo los items incluidos
                for (int i = 0; i < indexToIterate.size() - 1; i++) {
                    String valueToWrite = cursor.getString(indexToIterate.get(i));
                    String columnName = columnsToInclude.get(i);
                    if(configuration.getFieldsToConvert().containsKey(columnName))
                        valueToWrite = configuration.getFieldsToConvert().get(columnName).convert(valueToWrite);
                    arrStr[i]=valueToWrite;
                }
                csvWrite.writeNext(arrStr);
            }
            fileToReturn = file;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                cursor.close();
                csvWrite.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileToReturn;

        }
    }

}
