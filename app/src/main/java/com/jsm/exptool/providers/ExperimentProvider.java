package com.jsm.exptool.providers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.R;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.libs.MemoryUtils;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.data.repositories.ExperimentsRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    public static void endExperiment(Experiment experiment, Context context, MutableLiveData<Integer> responseLiveData, Experiment.ExperimentStatus targetStatus, boolean fromFinishPerformExperiment){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            experiment.setStatus(targetStatus);
            if(!fromFinishPerformExperiment) {
                Date maxDate = ExperimentsRepository.getMaxDateFromRegisters(experiment.getInternalId());
                Date minDate = ExperimentsRepository.getMinDateFromRegisters(experiment.getInternalId(), experiment.getEndDate());

                if (minDate == null) {
                    minDate = experiment.getInitDate();
                }
                long duration = maxDate.getTime() - minDate.getTime();
                if (duration < 0) {
                    duration = 0;
                }
                experiment.setDuration(duration + experiment.getDuration());
                experiment.setEndDate(maxDate);
            }
            if(experiment.getConfiguration().isEmbeddingEnabled()) {
                boolean embeddingPending = ImageRepository.getSynchronouslyPendingEmbeddingSyncRegistersByExperimentId(experiment.getInternalId()).size() > 0;
                experiment.setEmbeddingPending(embeddingPending);
            }else{
                experiment.setEmbeddingPending(false);
            }

            boolean syncPending = ExperimentsRepository.hasPendingSyncRegisters(experiment.getInternalId());
            experiment.setSyncPending(syncPending);

            String size = MemoryUtils.getFormattedFileSize(FilePathsProvider.getExperimentFilePath(context, experiment.getInternalId()));
            experiment.setSize(size);
            responseLiveData.postValue(ExperimentsRepository.updateExperiment(experiment));
        });

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
            Log.w(ExperimentProvider.class.getSimpleName(), e.getMessage(), e);
        }
        return clonedExperiment;
    }

}
