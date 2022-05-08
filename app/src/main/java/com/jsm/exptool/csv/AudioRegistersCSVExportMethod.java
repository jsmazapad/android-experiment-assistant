package com.jsm.exptool.csv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.model.register.AudioRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AudioRegistersCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public AudioRegistersCSVExportMethod() {
        super(AudioRegister.TABLE_NAME);
    }

    @Override
    protected List<String> createExcludedFields() {
        return new ArrayList<String>(){{
            add("fileDirectory");
        }};
    }

    @Override
    protected Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert() {
        return new HashMap<>();
    }
}
