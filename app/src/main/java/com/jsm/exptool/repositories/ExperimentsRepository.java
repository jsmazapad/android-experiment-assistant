package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.providers.ExperimentListFiltersProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class ExperimentsRepository {

    public static void getExperiments(MutableLiveData<ListResponse<Experiment>> responseLiveData, Experiment.ExperimentStatus statusFilter, ExperimentListFiltersProvider.ConditionFilterOptions conditionFilter, boolean conditionValue){
        responseLiveData.setValue(new ListResponse<>(DBHelper.getExperiments(statusFilter, conditionFilter, conditionValue)));
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




}
