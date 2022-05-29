package com.jsm.exptool.libs;

import static com.jsm.exptool.config.FrequencyConstants.DEFAULT_STEPS_FREQ;

import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

public class SeekbarSelectorHelper {

    private static final String DEFAULT_TAG = "DEFAULT_TAG";

    public static <T extends RepeatableElementConfig> void initFrequencySelector(ViewLayoutFrequencySelectorBinding includedSelectorBinding, FrequencySelectorListener listener, final int minValue, final int maxValue, final int initialValue) {
        initFrequencySelector(includedSelectorBinding, listener, minValue, maxValue, initialValue, DEFAULT_TAG);
    }

    public static <T extends RepeatableElementConfig> void initFrequencySelector(ViewLayoutFrequencySelectorBinding includedSelectorBinding, FrequencySelectorListener listener, final int minValue, final int maxValue, final int initialValue, String tag) {

        final TextView minDelayTV = includedSelectorBinding.minDelayTV;
        final TextView maxDelayTV = includedSelectorBinding.maxDelayTV;
        final TextView frequencyTV = includedSelectorBinding.frequencyTV;
        final ImageButton minusButton = includedSelectorBinding.minusButton;
        final ImageButton plusButton = includedSelectorBinding.plusButton;
        final SeekBar seekbarFrequency = includedSelectorBinding.seekbarFrequency;
        seekbarFrequency.setTag(tag);
        seekbarFrequency.setMax(maxValue);
        seekbarFrequency.setProgress(initialValue);
        seekbarFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = progress / DEFAULT_STEPS_FREQ;
                progress = progress * DEFAULT_STEPS_FREQ;

                changeFrequencyText(frequencyTV, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //No necesitado de momento
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < minValue) {
                    seekBar.setProgress(minValue);
                } else if (seekBar.getProgress() > maxValue) {
                    seekBar.setProgress(maxValue);
                }

                if (listener != null) {
                    listener.onSeekBarValueSelected(includedSelectorBinding.seekbarFrequency.getProgress(), includedSelectorBinding.seekbarFrequency);
                }
            }

        });


        minDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(minValue));
        maxDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(maxValue));
        changeFrequencyText(frequencyTV, includedSelectorBinding.seekbarFrequency.getProgress());
        minusButton.setOnClickListener(v -> {
            if (seekbarFrequency.getProgress() >= minValue + DEFAULT_STEPS_FREQ) {
                seekbarFrequency.setProgress(seekbarFrequency.getProgress() - DEFAULT_STEPS_FREQ);
                changeFrequencyText(frequencyTV, seekbarFrequency.getProgress());
            }
        });

        plusButton.setOnClickListener(v -> {
            if (seekbarFrequency.getProgress() <= maxValue - DEFAULT_STEPS_FREQ) {
                seekbarFrequency.setProgress(seekbarFrequency.getProgress() + DEFAULT_STEPS_FREQ);
                seekbarFrequency.invalidate();
                changeFrequencyText(frequencyTV, seekbarFrequency.getProgress());
            }
        });

    }

    private static void changeFrequencyText(TextView frequencyTV, int value) {
        frequencyTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));
    }

    public interface FrequencySelectorListener {
        void onSeekBarValueSelected(int value, SeekBar seekBar);
    }
}
