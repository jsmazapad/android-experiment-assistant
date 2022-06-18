package com.jsm.exptool.providers.export.csv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.entities.register.CommentRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommentRegistersCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public CommentRegistersCSVExportMethod() {
        super(CommentRegister.TABLE_NAME);
    }

    @Override
    protected List<String> createExcludedFields() {
        return new ArrayList<>();
    }

    @Override
    protected Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert() {
        return new HashMap<>();
    }
}
