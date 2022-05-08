package com.jsm.exptool.csv;

import android.database.Cursor;

import com.jsm.exptool.data.database.typeconverters.DoubleListConverter;
import com.jsm.exptool.libs.tabletocsv.TableToCSVConverterFunctionInterface;
import com.jsm.exptool.model.register.ImageRegister;
import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImageRegistersCSVExportMethod extends ExperimentsDatabaseTableToCSVExportMethod {

    private final static String EMBEDDING_COLUMN_NAME = "embedding";
    private final static int EMBEDDING_COLS_NUMBER = 1024;

    public ImageRegistersCSVExportMethod() {
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

    @Override
    public void writeCSVFromCursor(Cursor cursor, CSVWriter csvWriter) {
        //Creamos columnas de embedding
        List<String> embeddingColumns = new ArrayList<>(EMBEDDING_COLS_NUMBER);
        for(int i=0; i<EMBEDDING_COLS_NUMBER; i++){
            embeddingColumns.add(String.format("%s_%d",EMBEDDING_COLUMN_NAME, i));
        }
        List<String> columnsToInclude = new ArrayList<>(cursor.getColumnCount() - this.getExcludedFields().size() + embeddingColumns.size() - 1);
        List<Integer> indexToIterate = new ArrayList<>(columnsToInclude.size());
        //Extraemos las columnas que realmente obtendremos
        // y los índices de las mismas para poder obtener los valores posteriormente
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            if (!this.getExcludedFields().contains(cursor.getColumnName(i))) {
                if (cursor.getColumnName(i).equals(EMBEDDING_COLUMN_NAME)){
                    columnsToInclude.addAll(embeddingColumns);
                    indexToIterate.add(i);
                }else {
                    columnsToInclude.add(columnName);
                    indexToIterate.add(i);
                }
            }
        }

        csvWriter.writeNext(columnsToInclude.toArray(new String[columnsToInclude.size()]));
        while (cursor.moveToNext()) {
            List <String> rowToWrite = new ArrayList<>(columnsToInclude.size());

            //Recorremos sólo los items incluidos
            for (int i = 0; i < indexToIterate.size() - 1; i++) {
                int columnIndex = indexToIterate.get(i);
                String valueToWrite = cursor.getString(columnIndex);
                String columnName = columnsToInclude.get(i);
                if(this.getFieldsToConvert().containsKey(columnName))
                    valueToWrite = this.getFieldsToConvert().get(columnName).convert(valueToWrite);

                if(cursor.getColumnName(columnIndex).equals(EMBEDDING_COLUMN_NAME))
                {
                    List<Double> embeddingValues = DoubleListConverter.toList(valueToWrite);
                    List<String> embeddingValuesToWrite = new ArrayList<>(1024);
                    int embeddingValuesSize = embeddingValues.size();
                        for(int j= 0; j<EMBEDDING_COLS_NUMBER; j++)
                        {
                            if(j < embeddingValuesSize)
                            {
                                embeddingValuesToWrite.add(embeddingValues.get(j).toString());
                            }else{
                                embeddingValuesToWrite.add("");
                            }

                        }
                    rowToWrite.addAll(embeddingValuesToWrite);
                }else{
                    rowToWrite.add(valueToWrite);
                }

            }
            csvWriter.writeNext(rowToWrite.toArray(new String[columnsToInclude.size()]));
        }
    }
}
