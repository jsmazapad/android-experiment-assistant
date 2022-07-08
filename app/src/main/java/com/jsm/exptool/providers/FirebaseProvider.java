package com.jsm.exptool.providers;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseProvider {
    public static FirebaseApp getActiveInstance(){
        return FirebaseApp.getInstance(String.valueOf(PreferencesProvider.getFirebaseConfigVersionNumber()));
    }

    public static void initFirebase(Context c){
        if(FirebaseApp.getApps(c).size() > 0){
            PreferencesProvider.setFirebaseConfigVersionNumber(PreferencesProvider.getFirebaseConfigVersionNumber()+1);
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(PreferencesProvider.getFirebaseKey())
                .setApplicationId(PreferencesProvider.getFirebaseApp())
                .setProjectId(PreferencesProvider.getFirebaseProject())
                .setStorageBucket(PreferencesProvider.getFirebaseStorageBucket()).build();
        FirebaseApp.initializeApp(c, options, String.valueOf(PreferencesProvider.getFirebaseConfigVersionNumber()));
        FirebaseAuth.getInstance(FirebaseProvider.getActiveInstance()).addAuthStateListener(firebaseAuth -> {

            FirebaseUser user = FirebaseAuth.getInstance(FirebaseProvider.getActiveInstance()).getCurrentUser();
            if(user == null){
                SessionProvider.clearSession();
            }else{
                SessionProvider.setToken("FIREBASE");
            }
        });
    }
}
