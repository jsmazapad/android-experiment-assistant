package com.jsm.exptool.providers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jsm.exptool.R;
import com.jsm.exptool.core.utils.MemoryUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.util.ArrayList;

public class ExperimentProvider {

    public static String getTranslatableStringFromExperimentStatus(@NonNull Experiment.ExperimentStatus status, @NonNull Context context){
        String stringToReturn = "";
        switch (status){
            case CREATED:
                stringToReturn = context.getString(R.string.experiment_filter_name_create);
                break;
            case INITIATED:
                stringToReturn = context.getString(R.string.experiment_filter_name_init);
                break;
            case FINISHED:
                stringToReturn = context.getString(R.string.experiment_filter_name_finished);
                break;
        }
        return stringToReturn;
    }

    public static long endExperiment(Experiment experiment){
        experiment.setStatus(Experiment.ExperimentStatus.FINISHED);
        return ExperimentsRepository.updateExperiment(experiment);
    }

    public static String deleteExperiment(Context context, Experiment experiment){
        String error = "";
        //Eliminar archivos
        if (MemoryUtils.deleteDirectory(FilePathsProvider.getExperimentFilePath(context, experiment.getInternalId()))){
            //Eliminar registros de base datos
            if (ExperimentsRepository.deleteExperiment(experiment) < 1){
                error = "No se ha podido eliminar el experimento de la base de datos. Salga de esta pantalla y vuelva a intentarlo";
            }
        }else{
            error = "Error en la eliminación de los archivos del experimento";
        }


        return error;

    }

    public static Experiment createExperimentByCopyingExperimentConfiguration(Experiment experiment)  {
        Experiment clonedExperiment = null;
        try {
             clonedExperiment = (Experiment) experiment.clone();
            //Eliminamos referencia a iden base de datos para que no se interprete como el mismo
            //Al colocar 0 como id room sabe que tiene que colocarle un id autogenerado
            clonedExperiment.setInternalId(0);
            clonedExperiment.setTitle("");
            clonedExperiment.setDescription("");
            clonedExperiment.setSize("");
            clonedExperiment.setDuration(0);
            clonedExperiment.setStatus(Experiment.ExperimentStatus.CREATED);
            clonedExperiment.getConfiguration().setQuickComments(new ArrayList<>());
        } catch (CloneNotSupportedException e) {
            Log.e(ExperimentProvider.class.getSimpleName(), e.getMessage(), e);
        }
        return clonedExperiment;
    }

}
