package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

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
