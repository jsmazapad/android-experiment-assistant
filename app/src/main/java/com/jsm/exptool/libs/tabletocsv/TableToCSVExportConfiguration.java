package com.jsm.exptool.libs.tabletocsv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TableToCSVExportConfiguration {
    private String tableName;
    private final List<String> excludedFields;
    private final Map<String, TableToCSVConverterFunctionInterface> fieldsToConvert;


    protected TableToCSVExportConfiguration(String tableName){
        this.tableName = tableName;
        this.excludedFields = createExcludedFields();
        this.fieldsToConvert = createFieldsToConvert();
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getExcludedFields() {
        return excludedFields;
    }

    public Map<String, TableToCSVConverterFunctionInterface> getFieldsToConvert() {
        return fieldsToConvert;
    }

    protected abstract List<String> createExcludedFields();

    protected abstract Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert();
}
