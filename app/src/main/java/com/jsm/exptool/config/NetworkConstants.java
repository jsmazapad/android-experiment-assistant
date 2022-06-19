package com.jsm.exptool.config;

import androidx.annotation.StringRes;

import com.jsm.exptool.R;

import java.util.concurrent.TimeUnit;

public class NetworkConstants {
    public static final int MAX_RETRIES = 3;
    public static final int RETRY_DELAY = 1;
    public static final TimeUnit RETRY_DELAY_UNIT = TimeUnit.SECONDS;
    public static final String FIREBASE_BAD_TYPE = "Bad type";
    public static final String INVALID_REMOTE_SYNC_METHOD = "Bad sync method";

}
