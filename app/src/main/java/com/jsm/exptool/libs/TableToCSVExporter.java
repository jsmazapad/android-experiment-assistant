package com.jsm.exptool.libs;

import android.database.Cursor;

import androidx.room.Database;

import com.jsm.exptool.data.database.AppDatabase;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TableToCSVExporter {

    public static File exportToCsvFromTable(File file, String tableName, String filter, AppDatabase db) {
        File fileToReturn = null;
        CSVWriter csvWrite = null;
        Cursor cursor = null;
        try {
            cursor =  db.query("SELECT * FROM " + tableName + filter, null);
            csvWrite = new CSVWriter(new FileWriter(file));
        csvWrite.writeNext(cursor.getColumnNames());
        while (cursor.moveToNext()) {
            String arrStr[] = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount() - 1; i++)
                arrStr[i] = cursor.getString(i);
            csvWrite.writeNext(arrStr);
        }
        fileToReturn = file;

        } catch (IOException e) {
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
