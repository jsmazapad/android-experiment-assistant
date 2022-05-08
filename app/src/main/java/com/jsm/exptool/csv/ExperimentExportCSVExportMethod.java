package com.jsm.exptool.csv;

import com.jsm.exptool.data.database.typeconverters.StringListConverter;
import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.model.Experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExperimentExportCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public ExperimentExportCSVExportMethod() {
        super( Experiment.TABLE_NAME);
    }

    @Override
    protected List<String> createExcludedFields() {
        return new ArrayList<String>(){{
            add("initDate");
            add("endDate");
            //TODO ¿Eliminar este parámetro de la definición de la clase?
            add("audio_config_encodingBitRatesOptions");
        }};
    }

    @Override
    protected Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert() {
        return new HashMap<String, TableToCSVConverterFunctionInterface>(){{
            put("quickComments", field -> field.replace(StringListConverter.DELIMITER, ","));

        }};
    }

    @Override
    protected String getFilterVar() {
        return Experiment.COLUMN_ID;
    }
}
