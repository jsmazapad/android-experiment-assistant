package com.jsm.exptool.data.database.typeconverters;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Converter para objetos de tipo Date
 */
public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
