package com.jsm.exptool.providers.export.csv;

import android.database.Cursor;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.libs.tabletocsv.TableToCSVExportMethodInterface;
import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ExperimentsDatabaseTableToCSVExportMethod implements TableToCSVExportMethodInterface {
    private String tableName;
    private final List<String> excludedFields;
    private final Map<String, TableToCSVConverterFunctionInterface> fieldsToConvert;


    protected ExperimentsDatabaseTableToCSVExportMethod(String tableName){
        this.tableName = tableName;
        this.excludedFields = createExcludedFields();
        this.fieldsToConvert = createFieldsToConvert();
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<String> getExcludedFields() {
        return excludedFields;
    }

    @Override
    public Map<String, TableToCSVConverterFunctionInterface> getFieldsToConvert() {
        return fieldsToConvert;
    }

    protected abstract List<String> createExcludedFields();

    protected abstract Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert();

    @Override
    public String getQuery(){
        return "SELECT * FROM " + getTableName() + " WHERE "+ getFilterVar() +" = ?";
    }

    protected String getFilterVar(){
        return "experimentId";
    }

    @Override
    public void writeCSVFromCursor(Cursor cursor, CSVWriter csvWriter){
        List<String> columnsToInclude = new ArrayList<>(cursor.getColumnCount() - this.getExcludedFields().size());
        List<Integer> indexToIterate = new ArrayList<>(columnsToInclude.size());
        //Extraemos las columnas que realmente obtendremos
        // y los índices de las mismas para poder obtener los valores posteriormente
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            if (!this.getExcludedFields().contains(cursor.getColumnName(i))) {
                columnsToInclude.add(columnName);
                indexToIterate.add(i);
            }
        }

        csvWriter.writeNext(columnsToInclude.toArray(new String[columnsToInclude.size()]));
        while (cursor.moveToNext()) {
            String rowToWrite[] = new String[indexToIterate.size()];
            //Recorremos sólo los items incluidos
            for (int i = 0; i < indexToIterate.size(); i++) {
                String valueToWrite = cursor.getString(indexToIterate.get(i));
                String columnName = columnsToInclude.get(i);
                if(this.getFieldsToConvert().containsKey(columnName))
                    valueToWrite = this.getFieldsToConvert().get(columnName).convert(valueToWrite);
                rowToWrite[i]=valueToWrite;
            }
            csvWriter.writeNext(rowToWrite);
        }
    }
}
