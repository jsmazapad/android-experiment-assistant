package com.jsm.exptool.providers;

import android.content.Context;

import java.util.concurrent.TimeUnit;

public class TimeDisplayStringProvider {
    public static String millisecondsToStringBestDisplay(int milliseconds){
        String stringToReturn;
        long minutes
                = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds
                = (TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                % 60);
        long millisecondsRest = milliseconds - (minutes *60000) - (seconds * 1000);

        if (minutes == 0){
            if(seconds == 0){
                stringToReturn = String.format("%dms", milliseconds);
            }else{
                stringToReturn = String.format("%d,%ds", seconds, millisecondsRest/100);
            }
        }else{
            if(seconds == 0 && millisecondsRest == 0){
                stringToReturn = String.format("%dmin", minutes);
            }else{
                stringToReturn = String.format("%dmin %d,%ds", minutes, seconds, millisecondsRest/100);
            }
        }
        return stringToReturn;
    }
}
