package com.jsm.exptool.libs.tabletocsv;

import android.database.Cursor;

import com.opencsv.CSVWriter;

import java.util.List;
import java.util.Map;

public interface TableToCSVExportMethodInterface {
    String getTableName();

    List<String> getExcludedFields();

    Map<String, TableToCSVConverterFunctionInterface> getFieldsToConvert();

    String getQuery();

    void writeCSVFromCursor(Cursor cursor, CSVWriter csvWriter);
}
