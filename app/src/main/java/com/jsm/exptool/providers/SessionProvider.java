package com.jsm.exptool.providers;

public class SessionProvider {

    public static String getToken(){
        return PreferencesProvider.getSessionToken();
    }

    public static void setToken(String token){
        PreferencesProvider.setSessionToken(token);
    }

    public static boolean hasSession(){
        return !"".equals(PreferencesProvider.getSessionToken()) ;
    }

    public static void clearSession(){
        PreferencesProvider.setSessionToken("");
    }


}
