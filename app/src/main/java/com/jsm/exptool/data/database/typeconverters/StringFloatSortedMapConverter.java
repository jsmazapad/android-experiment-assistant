package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class StringFloatSortedMapConverter {
    private final static String DELIMITER = "@###@";
    private final static String KEY_VAL_DELIMITER = "@:@";


    @TypeConverter
    public static SortedMap<String, Float> toMap(String stringedList) {
        SortedMap<String, Float> map = new TreeMap<>();


        if (stringedList != null && !"".equals(stringedList)) {
            String[] values = stringedList.split(DELIMITER);

            for (int i = 0; i < values.length; i++) {
                String[] keyVal = values[i].split(KEY_VAL_DELIMITER);
                map.put(keyVal[0], Float.valueOf(keyVal[1]));
            }

        }
        return map;
    }

    @TypeConverter
    public static String toString(SortedMap<String, Float> map) {
        StringBuilder stringedList = new StringBuilder();
        if (map != null) {
            for (Map.Entry<String, Float> item : map.entrySet()) {
                String stringedItem = item.getKey() + KEY_VAL_DELIMITER + item.getValue();
                stringedList.append(stringedItem).append(DELIMITER);
            }
        }
        return stringedList.toString();
    }
}
