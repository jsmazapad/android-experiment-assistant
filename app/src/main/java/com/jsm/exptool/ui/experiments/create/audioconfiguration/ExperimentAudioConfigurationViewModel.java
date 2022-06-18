package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import static com.jsm.exptool.config.ConfigConstants.AUDIO_CONFIG_ARG;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.entities.experimentconfig.AudioRecordingOption;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class ExperimentAudioConfigurationViewModel extends BaseViewModel {

    private final AudioConfig audioConfig;
    private final List<AudioRecordingOption> audioOptions;
    private final MutableLiveData<List<Integer>> bitrateOptions = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<AudioRecordingOption> selectedAudioOption = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedAudioOptionPosition = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedBitrateOption = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedBitratePosition = new MutableLiveData<>();
    /**
     * Booleano que se usa como flag para la primera actualización de los datos
     */
    boolean isInitialConfig = false;


    public ExperimentAudioConfigurationViewModel(@NonNull Application application, AudioConfig audioConfig) {
        super(application);
        this.audioConfig = audioConfig;
        this.audioOptions = AudioProvider.getInstance().getAudioRecordingOptions();
        initDependantFields();
    }


    public List<AudioRecordingOption> getAudioOptions() {
        return audioOptions;
    }

    public MutableLiveData<AudioRecordingOption> getSelectedAudioOption() {
        return selectedAudioOption;
    }

    public MutableLiveData<List<Integer>> getBitrateOptions() {
        return bitrateOptions;
    }


    public MutableLiveData<Integer> getSelectedBitratePosition() {
        return selectedBitratePosition;
    }

    public MutableLiveData<Integer> getSelectedAudioOptionPosition() {
        return selectedAudioOptionPosition;
    }

    public void saveData(Context context) {

        audioConfig.setRecordingOption(selectedAudioOption.getValue());
        audioConfig.getRecordingOption().setSelectedEncodingBitRate(selectedBitrateOption.getValue());

        NavController navController = ((MainActivity) context).getNavController();
        NavBackStackEntry entry = navController.getPreviousBackStackEntry();
        if (entry != null) {
            entry.getSavedStateHandle().set(AUDIO_CONFIG_ARG, audioConfig);
        }
        navController.popBackStack();


    }

    public AudioRecordingOptionsSpinnerAdapter getAudioOptionsAdapter(Context context) {
        return new AudioRecordingOptionsSpinnerAdapter(context, audioOptions, R.layout.generic_title_description_spinner_list_item);
    }

    public BitrateSpinnerAdapter getBitRateAdapter(Context context) {
        return new BitrateSpinnerAdapter(context, bitrateOptions.getValue(), android.R.layout.simple_spinner_dropdown_item);
    }


    public void onSelectedAudioOption(int position) {
        //Al seleccionar elemento del spinner:
        // 1.actualizamos el elemento guardado,
        // 2.actualizamos la lista de bitrates y
        // 3.seleccionamos la primera

        selectedAudioOption.setValue(audioOptions.get(position));
        if (!isInitialConfig) {
            bitrateOptions.setValue(selectedAudioOption.getValue().getEncodingBitRatesOptions());
            selectedBitratePosition.setValue(0);
            onBitrateSelected(0);
        } else {
            isInitialConfig = false;
        }
    }

    public void onBitrateSelected(int position) {
        selectedBitrateOption.setValue(bitrateOptions.getValue().get(position));
    }

    public void initDependantFields() {
        AudioRecordingOption initialOption = audioConfig.getRecordingOption();
        if (initialOption != null) {

            int selectedAudioOptionPositionIndex = audioOptions.indexOf(audioConfig.getRecordingOption() != null ? audioConfig.getRecordingOption() : audioOptions.get(0));
            if (selectedAudioOptionPositionIndex >= 0 && selectedAudioOptionPositionIndex < audioOptions.size()) {
                selectedAudioOptionPosition.setValue(selectedAudioOptionPositionIndex);
                bitrateOptions.setValue(initialOption.getEncodingBitRatesOptions());
            } else {
                selectedAudioOptionPosition.setValue(0);
            }
            int selectedBitratePositionIndex = bitrateOptions.getValue().indexOf(initialOption.getSelectedEncodingBitRate());
            if (selectedBitratePositionIndex >= 0 && selectedBitratePositionIndex < bitrateOptions.getValue().size()) {
                selectedBitratePosition.setValue(selectedBitratePositionIndex);
                onBitrateSelected(selectedBitratePositionIndex);
                //Colocamos el flag de configuración inicial para que no se machaque el valor de selectedBitratePosition
                isInitialConfig = true;

            }
        }
    }


}