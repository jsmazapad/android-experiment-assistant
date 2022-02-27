package com.jsm.exptool.libs;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class AudioHandler {

    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private int audioSource = MediaRecorder.AudioSource.MIC;
    private int outputFormat = MediaRecorder.OutputFormat.AMR_NB;
    private int audioEncoder = MediaRecorder.AudioEncoder.AMR_NB;
    private int encodingBitRate = 4400;


    private boolean startPlaying(File file){
        player = new MediaPlayer();
        try {
            player.setDataSource(file.getPath());
            player.prepare();
            player.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private boolean startRecording(File file) {

        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(audioSource);
            recorder.setOutputFormat(outputFormat);
            recorder.setAudioEncodingBitRate(encodingBitRate);
            recorder.setOutputFile(file.getPath());
            recorder.setAudioEncoder(audioEncoder);
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public MediaRecorder getRecorder() {
        return recorder;
    }

    public void setRecorder(MediaRecorder recorder) {
        this.recorder = recorder;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(int outputFormat) {
        this.outputFormat = outputFormat;
    }

    public int getAudioEncoder() {
        return audioEncoder;
    }

    public void setAudioEncoder(int audioEncoder) {
        this.audioEncoder = audioEncoder;
    }

    public int getEncodingBitRate() {
        return encodingBitRate;
    }

    public void setEncodingBitRate(int encodingBitRate) {
        this.encodingBitRate = encodingBitRate;
    }
}
