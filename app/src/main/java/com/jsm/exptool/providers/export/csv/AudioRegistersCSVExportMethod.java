package com.jsm.exptool.providers.export.csv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.entities.register.AudioRegister;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AudioRegistersCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public AudioRegistersCSVExportMethod() {
        super(AudioRegister.TABLE_NAME);
    }

    @Override
    protected List<String> createExcludedFields() {
        return Collections.singletonList("fileDirectory");
    }

    @Override
    protected Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert() {
        return new HashMap<>();
    }
}
