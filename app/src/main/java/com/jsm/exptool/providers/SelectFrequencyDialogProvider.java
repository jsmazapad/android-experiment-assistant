package com.jsm.exptool.providers;

import static com.jsm.exptool.config.SensorConfigConstants.DEFAULT_STEPS_FREQ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jsm.exptool.R;
import com.jsm.exptool.databinding.DialogSelectFrequencyBinding;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.SensorConfigurationVO;


public class SelectFrequencyDialogProvider {
    //TODO Refactorizar interface comun entre cámara imagen y sensores
    public static void createDialog(Context context, SensorConfigurationVO sensorConfiguration, OnFrequencySelectedListener listener, final int minValue, final int maxValue) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        DialogSelectFrequencyBinding binding = DialogSelectFrequencyBinding.inflate(layoutInflaterAndroid);
        View mView = binding.getRoot();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        mBuilder.setTitle(String.format(context.getString(R.string.configure_frequency_title), context.getString(sensorConfiguration.getSensor().getRName())));

        final TextView minDelayTV = binding.minDelayTV;
        final TextView maxDelayTV = binding.maxDelayTV;
        final TextView frequencyTV = binding.frequencyTV;
        final ImageButton minusButton = binding.minusButton;
        final ImageButton plusButton = binding.plusButton;
        final SwitchMaterial defaultFreqSwitch = binding.switchSensorGlobalFrequency;
        defaultFreqSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            sensorConfiguration.setDefaultConfigurationEnabled(checked);
            binding.frequencySelectorLL.setVisibility(checked? View.GONE : View.VISIBLE);
        });
        defaultFreqSwitch.setChecked(sensorConfiguration.isDefaultConfigurationEnabled());
        final SeekBar slider = binding.seekbarFrequency;
        slider.setMax((int) maxValue);
        slider.setProgress(sensorConfiguration.getSensor().getInterval());
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = progress / DEFAULT_STEPS_FREQ;
                progress = progress * DEFAULT_STEPS_FREQ;
//

                changeFrequencyText(frequencyTV, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress() < minValue) {
                    seekBar.setProgress((int)minValue);
                }else if(seekBar.getProgress() > maxValue) {
                    seekBar.setProgress((int)maxValue);
                }
            }

        });


        minDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(context, minValue));
        maxDelayTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(context, maxValue));
        changeFrequencyText(frequencyTV, sensorConfiguration.getSensor().getInterval());


        mBuilder.setPositiveButton(R.string.default_modal_okButton, (dialog, which) -> {
            sensorConfiguration.getSensor().setInterval((int) slider.getProgress());
            listener.onFrequencySelected(sensorConfiguration);
        });
        mBuilder.setNegativeButton(R.string.default_modal_cancelButton, (dialog, which) -> dialog.cancel());

        minusButton.setOnClickListener(v -> {
            if (slider.getProgress() >= sensorConfiguration.getSensor().getIntervalMin() + DEFAULT_STEPS_FREQ && slider.getProgress() >= minValue + DEFAULT_STEPS_FREQ) {
                slider.setProgress(slider.getProgress() - DEFAULT_STEPS_FREQ);
                changeFrequencyText(frequencyTV, slider.getProgress());
            }
        });

        plusButton.setOnClickListener(v -> {
            if (slider.getProgress() <= maxValue - DEFAULT_STEPS_FREQ) {
                slider.setProgress(slider.getProgress() + DEFAULT_STEPS_FREQ);
                slider.invalidate();
                changeFrequencyText(frequencyTV, slider.getProgress());
            }
        });

        AlertDialog alertDialogAndroid = mBuilder.create();
        alertDialogAndroid.show();
    }

    private static void changeFrequencyText(TextView frequencyTV, int value){
        frequencyTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(frequencyTV.getContext(), value));
    }

    public interface OnFrequencySelectedListener{
        void onFrequencySelected(SensorConfigurationVO sensorConfiguration);
    }
}
