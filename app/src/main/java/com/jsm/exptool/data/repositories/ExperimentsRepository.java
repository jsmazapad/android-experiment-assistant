package com.jsm.exptool.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.providers.ExperimentListFiltersProvider;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExperimentsRepository {

    public static void getExperiments(MutableLiveData<ListResponse<Experiment>> responseLiveData, Experiment.ExperimentStatus statusFilter, ExperimentListFiltersProvider.ConditionFilterOptions conditionFilter, boolean conditionValue){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.postValue(new ListResponse<>(DBHelper.getExperiments(statusFilter, conditionFilter, conditionValue))));
    }

    public static long registerExperiment(Experiment experiment){
        return DBHelper.insertExperiment(experiment);
    }

    public static int updateExperiment(Experiment experiment){
        return DBHelper.updateExperiment(experiment);
    }

    public static Experiment getExperimentById(long experimentId){
        return DBHelper.getExperimentById(experimentId);
    }

    public static int deleteExperiment(Experiment experiment){
        return DBHelper.deleteExperimentById(experiment);
    }

    public static Date getMaxDateFromRegisters(long experimentId) {
        return DBHelper.getMaxDateFromRegisters(experimentId);
    }

    public static Date getMinDateFromRegisters(long experimentId, Date dateFrom) {
            return DBHelper.getMinDateFromRegisters(experimentId, dateFrom);
    }

    public static boolean hasPendingSyncRegisters(long experimentId) {
        return DBHelper.hasPendingSyncRegisters(experimentId);
    }




}
