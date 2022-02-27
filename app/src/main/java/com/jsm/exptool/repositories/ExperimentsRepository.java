package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.Experiment;

import java.util.ArrayList;

public class ExperimentsRepository {

    public static void getExperiments(MutableLiveData<ListResponse<Experiment>> responseLiveData){
        responseLiveData.setValue(new ListResponse<>(DBHelper.getExperiments()));
    }

    public static long registerExperiment(Experiment experiment){
        return DBHelper.insertExperiment(experiment);
    }

    public static int updateExperiment(Experiment experiment){
        return DBHelper.updateExperiment(experiment);
    }


}
