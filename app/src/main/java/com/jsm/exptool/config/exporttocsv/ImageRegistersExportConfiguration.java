package com.jsm.exptool.config.exporttocsv;

import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.libs.tabletocsv.TableToCSVExportConfiguration;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ImageRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImageRegistersExportConfiguration extends TableToCSVExportConfiguration {
    public ImageRegistersExportConfiguration() {
        super(ImageRegister.TABLE_NAME);
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
