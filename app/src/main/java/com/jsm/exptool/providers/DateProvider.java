package com.jsm.exptool.providers;


import com.jsm.exptool.config.ConfigConstants;
import com.jsm.exptool.core.utils.DateUtils;

import java.util.Date;

public class DateProvider {


    public static String dateToDisplayStringWithTime(Date date){
        String dateString = "";
        try {
            dateString = DateUtils.dateFormat(date, ConfigConstants.DISPLAY_DATE_WITH_TIME_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

}
