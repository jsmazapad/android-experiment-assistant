package com.jsm.exptool.providers;

import com.jsm.exptool.libs.AudioHandler;


public class AudioProvider {

    AudioHandler audioHandler;

    private static AudioProvider INSTANCE = null;

    private AudioProvider() {
        audioHandler = new AudioHandler();
    };

    public static synchronized AudioProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioProvider();
        }
        return(INSTANCE);
    }
}
