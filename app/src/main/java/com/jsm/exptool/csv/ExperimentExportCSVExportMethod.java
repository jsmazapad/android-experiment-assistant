package com.jsm.exptool.csv;

import com.jsm.exptool.data.database.typeconverters.StringListConverter;
import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.entities.Experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExperimentExportCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {
    public ExperimentExportCSVExportMethod() {
        super( Experiment.TABLE_NAME);
    }

    @Override
    protected List<String> createExcludedFields() {
        return Arrays.asList(
            "initDate",
            "endDate",
            //TODO ¿Eliminar este parámetro de la definición de la clase?
            "audio_config_encodingBitRatesOptions"
       );
    }

    @Override
    protected Map<String, TableToCSVConverterFunctionInterface> createFieldsToConvert() {
        HashMap<String, TableToCSVConverterFunctionInterface> mapToReturn = new HashMap<>();
        mapToReturn.put("quickComments", field -> field.replace(StringListConverter.DELIMITER, ","));
        return mapToReturn;
    }

    @Override
    protected String getFilterVar() {
        return Experiment.COLUMN_ID;
    }
}
