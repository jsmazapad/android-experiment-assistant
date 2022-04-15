package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter {
    private final static String DELIMITER = "@###@";


    @TypeConverter
    public static List<String> toList(String stringedList) {
        List<String> list = new ArrayList<>();


        if(stringedList != null && !"".equals(stringedList)) {
            String [] values = stringedList.split(DELIMITER);
            String [] parsed = new String[values.length];

                for (int i = 0; i < values.length; i++) parsed[i] = values[i];
                list = Arrays.asList(parsed);
        }
        return list;
    }

    @TypeConverter
    public static String toString(List<String> list) {
        StringBuilder stringedList = new StringBuilder();
        if(list != null) {
            for(String item:list){
                stringedList.append(item).append(DELIMITER);
            }
        }
        return stringedList.toString();
    }
}
