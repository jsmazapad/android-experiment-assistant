package com.jsm.exptool.config;

import android.media.MediaRecorder;

import com.jsm.exptool.R;
import com.jsm.exptool.model.AudioRecordingOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioConfigConstants {
//    public static final ArrayList<AudioRecordingOption> SUPPORTED_AUDIO_CONFIGURATIONS = new ArrayList<AudioRecordingOption>() {{
//        add(new AudioRecordingOption("AMR Narrow Band", R.string.AMR_NB_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AMR_NB,
//                MediaRecorder.AudioEncoder.AMR_NB, "3gp", 4750,
//                new ArrayList<>(Arrays.asList(4750, 5150, 5900, 6700, 7400, 7950, 10200, 12200))));
//        add(new AudioRecordingOption("AMR Wide Band", R.string.AMR_WB_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AMR_WB,
//                MediaRecorder.AudioEncoder.AMR_WB, "3gp", 6600,
//                new ArrayList<>(Arrays.asList(6600, 8850, 12650, 14250, 15850, 18250, 19850, 23050))));
//        add(new AudioRecordingOption("AAC", R.string.AAC_HE_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AAC_ADTS,
//                MediaRecorder.AudioEncoder.HE_AAC, "3gp", 16000,
//                new ArrayList<>(Arrays.asList(16000, 32000))));
//
//    }};

    public static final List<AudioRecordingOption> SUPPORTED_AUDIO_CONFIGURATIONS = Arrays.asList(
            new AudioRecordingOption("AMR Narrow Band", R.string.AMR_NB_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AMR_NB,
                    MediaRecorder.AudioEncoder.AMR_NB, "3gp", 4750,
                    new ArrayList<>(Arrays.asList(4750, 5150, 5900, 6700, 7400, 7950, 10200, 12200))),
            new AudioRecordingOption("AMR Wide Band", R.string.AMR_WB_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AMR_WB,
                    MediaRecorder.AudioEncoder.AMR_WB, "3gp", 6600,
                    new ArrayList<>(Arrays.asList(6600, 8850, 12650, 14250, 15850, 18250, 19850, 23050))),
            new AudioRecordingOption("AAC", R.string.AAC_HE_description, MediaRecorder.AudioSource.MIC, MediaRecorder.OutputFormat.AAC_ADTS,
                    MediaRecorder.AudioEncoder.HE_AAC, "3gp", 16000,
                    new ArrayList<>(Arrays.asList(16000, 32000))));

}
