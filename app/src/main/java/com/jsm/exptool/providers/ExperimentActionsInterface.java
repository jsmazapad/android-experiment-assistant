package com.jsm.exptool.providers;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.model.Experiment;

public interface ExperimentActionsInterface {

    /**
     * Inicia la realización de un experimento
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void initExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Visualiza los datos de un experimento
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void viewExperimentData(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Exporta el contenido de un experimento a un archivo zip incluyendo sus datos esportados csv y los ficheros asociados a este
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void exportExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Sincroniza el contenido de un experimento con la base de datos
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void syncExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Finaliza un experimento
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void endExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Continua un experimento existente
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void continueExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    /**
     * Elimina un experimento, incluyendo todos los ficheros y registros de base de datos asociados a este
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void deleteExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

    /**
     * Crea un experimento nuevo copiando la configuración de otro existente
     * @param context
     * @param experiment
     * @param alertDialog
     */
    void createExperimentByCopyingExperimentConfig(Context context, Experiment experiment, AlertDialog alertDialog);

    MutableLiveData<String> getSensorCountValue();
    MutableLiveData<String> getImageCountValue();
    MutableLiveData<String> getEmbeddingCountValue();
    MutableLiveData<String> getAudioCountValue();
    MutableLiveData<String> getCommentsCountValue();

    String getSensorsEnabledText();

    String getImagesEnabledText();

    String getEmbeddingEnabledText();

    String getAudioEnabledText();

    String getCommentsEnabledText();
    String getSensorsListText();

    String getImagesConfigText();
    String getEmbeddingConfigText();
    String getAudioConfigText();
    String getQuickCommentsText();

    LiveData<Boolean> getIsLoading();


}
