package com.jsm.exptool.providers;

import com.jsm.exptool.config.AudioConfigConstants;
import com.jsm.exptool.libs.AudioHandler;
import com.jsm.exptool.entities.AudioRecordingOption;

import java.io.File;
import java.util.List;


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

    public List<AudioRecordingOption> getAudioRecordingOptions(){
        return AudioConfigConstants.SUPPORTED_AUDIO_CONFIGURATIONS;
    }

    public boolean record(File file, AudioRecordingOption recordingOption){
        return audioHandler.startRecording(file, recordingOption.getAudioSource(), recordingOption.getOutputFormat(), recordingOption.getSelectedEncodingBitRate(), recordingOption.getAudioEncoder());
    }

    public void stopRecording(){
        audioHandler.stopRecording();
    }

    public boolean play(File f){
        return audioHandler.startPlaying(f);
    }

    public void stopPlaying(){
        audioHandler.stopPlaying();
    }




}
