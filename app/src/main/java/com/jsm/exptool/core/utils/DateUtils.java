package com.jsm.exptool.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static Date dateParse(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("es", "ES"));
        return sdf.parse(date);
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, new Locale("es", "ES"));
        return sdf.format(date);
    }
}
