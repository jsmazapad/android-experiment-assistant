package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.entities.AudioRecordingOption;

import java.util.List;

public class AudioRecordingOptionsSpinnerAdapter extends BaseSpinnerAdapter<AudioRecordingOption, AudioRecordingOptionSpinnerViewHolder> {
    public AudioRecordingOptionsSpinnerAdapter(Context context, List<AudioRecordingOption> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public AudioRecordingOptionSpinnerViewHolder instanceViewHolder(View v) {
        return new AudioRecordingOptionSpinnerViewHolder(v);
    }
}
