package com.jsm.exptool.providers;


import android.util.Log;

import com.jsm.exptool.config.ConfigConstants;
import com.jsm.exptool.libs.DateUtils;

import java.util.Date;

public class DateProvider {


    public static String dateToDisplayStringWithTime(Date date){
        String dateString = "";
        try {
            dateString = DateUtils.dateFormat(date, ConfigConstants.DISPLAY_DATE_WITH_TIME_FORMAT);
        } catch (Exception e) {
            Log.w(DateProvider.class.getSimpleName(), e.getMessage(), e);
        }
        return dateString;
    }

}
