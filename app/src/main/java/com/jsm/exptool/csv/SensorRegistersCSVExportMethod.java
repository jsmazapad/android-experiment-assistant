package com.jsm.exptool.csv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.entities.register.SensorRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SensorRegistersCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public SensorRegistersCSVExportMethod() {
        super(SensorRegister.TABLE_NAME);
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
