package com.jsm.and_image_analytics_poc.ui.configuration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.jsm.and_image_analytics_poc.core.ui.base.BaseViewModel;
import com.jsm.and_image_analytics_poc.providers.PreferencesProvider;

public class ConfigurationViewModel extends BaseViewModel {

    String username;
    String password;
    String serverUrl;

    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        username = PreferencesProvider.getUser();
        password = PreferencesProvider.getPassword();
        serverUrl = PreferencesProvider.getRemoteServer();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void savePreferences(){
        PreferencesProvider.setUser(username);
        PreferencesProvider.setPassword(password);
        PreferencesProvider.setRemoteServer(serverUrl);
    }

}
