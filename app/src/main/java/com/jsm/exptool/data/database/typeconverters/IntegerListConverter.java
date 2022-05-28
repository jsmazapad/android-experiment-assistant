package com.jsm.exptool.data.database.typeconverters;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Converter para insertar listas de Integer,
 * Room no las entiende y hay que proporcionarle conversores de datos Custom
 */
public class IntegerListConverter {

    private final static String DELIMITER = "@###@";


    @TypeConverter
    public static List<Integer> toList(String stringedList) {
        List<Integer> list = new ArrayList<>();


        if(stringedList != null && !"".equals(stringedList)) {
            String [] values = stringedList.split(DELIMITER);
            Integer [] parsed = new Integer[values.length];
            try {
                for (int i = 0; i < values.length; i++) parsed[i] = Integer.parseInt(values[i]);
                list = Arrays.asList(parsed);
            }catch (NumberFormatException e){
                Log.e(IntegerListConverter.class.getSimpleName(), e.getMessage(), e);
                return new ArrayList<>();
            }
        }
        return list;
    }

    @TypeConverter
    public static String toString(List<Integer> list) {
        String stringedList = "";
        if(list != null) {
            for(Integer item:list){
                stringedList+= item + DELIMITER;
            }
        }
        return stringedList;
    }
}
