package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.AudioRecordingOption;

public class AudioRecordingOptionSpinnerViewHolder extends BaseRecyclerViewHolder<AudioRecordingOption> {


    TextView titleTV;
    TextView descriptionTV;

    protected AudioRecordingOptionSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        descriptionTV = v.findViewById(R.id.descriptionTV);
    }

    @Override
    public void fillViewHolder(AudioRecordingOption element) {
        titleTV.setText(element.getDisplayName());
        descriptionTV.setText(element.getDescriptionTranslatableRes());

    }
}
