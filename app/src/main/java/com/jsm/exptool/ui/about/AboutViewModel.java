package com.jsm.exptool.ui.about;

import android.app.Application;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.jsm.exptool.BR;
import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseViewModel;

public class AboutViewModel extends BaseViewModel {

    private final String appName;
    private final String appVersion;
    private final String additionalInfo;

    public AboutViewModel(@NonNull Application application) {
        super(application);
        appVersion = "V "+ BuildConfig.VERSION_NAME + " R" + BuildConfig.VERSION_CODE;
        appName = getApplication().getString(R.string.app_name);
        additionalInfo = Html.fromHtml(getApplication().getString(R.string.about_text)).toString();
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}