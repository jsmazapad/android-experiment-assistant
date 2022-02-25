package com.jsm.exptool.providers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jsm.exptool.R;
import com.jsm.exptool.databinding.DialogSelectFrequencyBinding;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.model.FrequencyConfigurationVO;
import com.jsm.exptool.model.RepeatableElement;
import com.jsm.exptool.ui.experiments.create.configure.FrequencySelectorHelper;


public class SelectFrequencyDialogProvider {
    //TODO Refactorizar interface comun entre cámara, imagen y sensores
    public static <T extends RepeatableElement> void  createDialog(Context context, FrequencyConfigurationVO<T> sensorConfiguration, OnFrequencySelectedListener listener, final int minValue, final int maxValue) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        DialogSelectFrequencyBinding binding = DialogSelectFrequencyBinding.inflate(layoutInflaterAndroid);
        View mView = binding.getRoot();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        mBuilder.setTitle(String.format(context.getString(R.string.configure_frequency_title), context.getString(sensorConfiguration.getRepeatableElement().getNameStringResource())));
        ViewLayoutFrequencySelectorBinding includedSelectorBinding = binding.frequencySelectorIncluded;
        FrequencySelectorHelper.initFrequencySelector( includedSelectorBinding,  sensorConfiguration,  null, minValue, maxValue);
        final SwitchMaterial defaultFreqSwitch = binding.switchSensorGlobalFrequency;
        defaultFreqSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            sensorConfiguration.setDefaultConfigurationEnabled(checked);
            binding.frequencySelectorLL.setVisibility(checked? View.GONE : View.VISIBLE);
        });
        defaultFreqSwitch.setChecked(sensorConfiguration.isDefaultConfigurationEnabled());


        mBuilder.setPositiveButton(R.string.default_modal_okButton, (dialog, which) -> {
            sensorConfiguration.getRepeatableElement().setInterval((int) includedSelectorBinding.seekbarFrequency.getProgress());
            listener.onFrequencySelected(sensorConfiguration);
        });
        mBuilder.setNegativeButton(R.string.default_modal_cancelButton, (dialog, which) -> dialog.cancel());



        AlertDialog alertDialogAndroid = mBuilder.create();
        alertDialogAndroid.show();
    }



    public interface OnFrequencySelectedListener{
        <T extends RepeatableElement> void onFrequencySelected(FrequencyConfigurationVO<T> sensorConfiguration);
    }
}
