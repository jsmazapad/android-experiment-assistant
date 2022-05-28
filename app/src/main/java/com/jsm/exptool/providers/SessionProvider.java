package com.jsm.exptool.providers;

public class SessionProvider {

    public static String getToken(){
        return PreferencesProvider.getSessionToken();
    }

    public static void setToken(String token){
        PreferencesProvider.setSessionToken(token);
    }

    public static void clearSession(){
        PreferencesProvider.setSessionToken("");
    }

}
