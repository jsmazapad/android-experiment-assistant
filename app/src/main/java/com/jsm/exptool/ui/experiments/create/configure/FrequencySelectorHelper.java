package com.jsm.exptool.ui.experiments.create.configure;

import static com.jsm.exptool.config.SensorConfigConstants.DEFAULT_STEPS_FREQ;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.model.FrequencyConfigurationVO;
import com.jsm.exptool.model.Repeatable;
import com.jsm.exptool.providers.SelectFrequencyDialogProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

public class FrequencySelectorHelper {

    public static <T extends Repeatable> void initFrequencySelector(ViewLayoutFrequencySelectorBinding includedSelectorBinding, FrequencyConfigurationVO<T> sensorConfiguration, SelectFrequencyDialogProvider.OnFrequencySelectedListener listener, final int minValue, final int maxValue) {

        final TextView minDelayTV = includedSelectorBinding.minDelayTV;
        final TextView maxDelayTV = includedSelectorBinding.maxDelayTV;
        final TextView frequencyTV = includedSelectorBinding.frequencyTV;
        final ImageButton minusButton = includedSelectorBinding.minusButton;
        final ImageButton plusButton = includedSelectorBinding.plusButton;
        final SeekBar seekbarFrequency = includedSelectorBinding.seekbarFrequency;
        seekbarFrequency.setMax((int) maxValue);
        seekbarFrequency.setProgress(sensorConfiguration.getRepeatableElement().getInterval());
        seekbarFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = progress / DEFAULT_STEPS_FREQ;
                progress = progress * DEFAULT_STEPS_FREQ;

                changeFrequencyText(frequencyTV, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < minValue) {
                    seekBar.setProgress((int) minValue);
                } else if (seekBar.getProgress() > maxValue) {
                    seekBar.setProgress((int) maxValue);
                }

                if (listener != null) {
                    sensorConfiguration.getRepeatableElement().setInterval((int) includedSelectorBinding.seekbarFrequency.getProgress());
                    listener.onFrequencySelected(sensorConfiguration);
                }
            }

        });


        minDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(minValue));
        maxDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(maxValue));
        changeFrequencyText(frequencyTV, sensorConfiguration.getRepeatableElement().getInterval());
        minusButton.setOnClickListener(v -> {
            if (seekbarFrequency.getProgress() >= sensorConfiguration.getRepeatableElement().getIntervalMin() + DEFAULT_STEPS_FREQ && seekbarFrequency.getProgress() >= minValue + DEFAULT_STEPS_FREQ) {
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
}
