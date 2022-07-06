package com.jsm.exptool.providers;

import static com.jsm.exptool.ui.experiments.create.configure.ExperimentCreateConfigureDataViewModel.CONFIGURING_AUDIO_DURATION_TAG;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jsm.exptool.R;
import com.jsm.exptool.databinding.DialogSelectFrequencyBinding;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;


public class SelectFrequencyDialogProvider {
    //TODO Refactorizar interface comun entre cámara, imagen y sensores
    public static <T extends RepeatableElementConfig> void  createDialog(Context context, T frequencyConfiguration, OnFrequencySelectedListener listener, final int minValue, final int maxValue, final int initialValue, boolean showGlobal, @Nullable String selectedAttributeTag, @Nullable String alternativeTitle) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DialogSelectFrequencyBinding binding = DialogSelectFrequencyBinding.inflate(layoutInflater);
        View mView = binding.getRoot();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        if(alternativeTitle == null) {
            mBuilder.setTitle(String.format(context.getString(R.string.configure_frequency_title), context.getString(frequencyConfiguration.getNameStringRes())));
        }else{
            mBuilder.setTitle(alternativeTitle);
        }
        ViewLayoutFrequencySelectorBinding includedSelectorBinding = binding.frequencySelectorIncluded;
        SeekbarSelectorProvider.initFrequencySelector( includedSelectorBinding,null, minValue, maxValue, initialValue);
        final SwitchMaterial defaultFreqSwitch = binding.switchSensorGlobalFrequency;

        if(frequencyConfiguration instanceof SensorConfig && showGlobal) {

            SensorConfig sensorConfiguration = (SensorConfig)frequencyConfiguration;
            defaultFreqSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
                sensorConfiguration.setDefaultConfigurationEnabled(checked);
                binding.frequencySelectorLL.setVisibility(checked ? View.GONE : View.VISIBLE);
            });
            defaultFreqSwitch.setChecked(sensorConfiguration.isDefaultConfigurationEnabled());
        }else{
            defaultFreqSwitch.setVisibility(View.GONE);
            defaultFreqSwitch.setChecked(false);
        }


        mBuilder.setPositiveButton(R.string.default_modal_okButton, (dialog, which) -> {
            if(CONFIGURING_AUDIO_DURATION_TAG.equals(selectedAttributeTag)) {
                ((AudioConfig)frequencyConfiguration).setRecordingDuration(includedSelectorBinding.seekbarFrequency.getProgress());
            }else{
                frequencyConfiguration.setInterval(includedSelectorBinding.seekbarFrequency.getProgress());
            }
            listener.onFrequencySelected(frequencyConfiguration, selectedAttributeTag);
        });
        mBuilder.setNegativeButton(R.string.default_modal_cancelButton, (dialog, which) -> dialog.cancel());



        AlertDialog alertDialogAndroid = mBuilder.create();
        alertDialogAndroid.show();
    }



    public interface OnFrequencySelectedListener{
        <T extends RepeatableElementConfig> void onFrequencySelected(T sensorConfiguration, String selectedAttributeTag);
    }
}
