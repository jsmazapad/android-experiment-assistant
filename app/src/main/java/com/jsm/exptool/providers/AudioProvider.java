package com.jsm.exptool.providers;

import com.jsm.exptool.config.AudioConfigConstants;
import com.jsm.exptool.libs.AudioHandler;
import com.jsm.exptool.model.AudioRecordingOption;

import java.io.File;
import java.util.ArrayList;


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

    public ArrayList<AudioRecordingOption> getAudioRecordingOptions(){
        return AudioConfigConstants.SUPPORTED_AUDIO_CONFIGURATIONS;
    }

    boolean record(File file, AudioRecordingOption recordingOption){
        return audioHandler.startRecording(file, recordingOption.getAudioSource(), recordingOption.getOutputFormat(), recordingOption.getSelectedEncodingBitRate(), recordingOption.getAudioEncoder());
    }

    void stopRecording(){
        audioHandler.stopRecording();
    }

    boolean play(File f){
        return audioHandler.startPlaying(f);
    }

    void stopPlaying(){
        audioHandler.stopPlaying();
    }




}
