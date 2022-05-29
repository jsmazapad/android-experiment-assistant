package com.jsm.exptool.libs;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class AudioHandler {

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isRecording = false;
    private boolean isPlaying = false;


    public boolean startPlaying(File file) {

        try {
            if (isPlaying) {
                stopPlaying();
            }
            isPlaying = true;
            player = new MediaPlayer();
            player.setDataSource(file.getPath());
            player.prepare();
            player.start();
            return true;
        } catch (Exception e) {
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
            return false;
        }
    }

    public void stopPlaying() {
        isPlaying = false;
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public boolean startRecording(File file, int audioSource, int outputFormat, int encodingBitRate, int audioEncoder) {

        try {
            if (isRecording) {
                stopRecording();
            }
            isRecording = true;
            recorder = new MediaRecorder();
            recorder.setAudioSource(audioSource);
            recorder.setOutputFormat(outputFormat);
            recorder.setAudioEncodingBitRate(encodingBitRate);
            recorder.setOutputFile(file.getPath());
            recorder.setAudioEncoder(audioEncoder);
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
        }


        return true;
    }

    public void stopRecording() {
        isRecording = false;
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
            }catch (Exception e){
                Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }


}
