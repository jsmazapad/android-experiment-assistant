package com.jsm.exptool.providers;

import android.content.Context;

import com.jsm.exptool.model.Experiment;

public interface ExperimentActionsInterface {

    void initExperiment(Context context, Experiment experiment);
    void viewExperimentData(Context context, Experiment experiment);
    void exportExperiment(Context context, Experiment experiment);
    void syncExperiment(Context context, Experiment experiment);
    void endExperiment(Context context, Experiment experiment);
    void continueExperiment(Context context, Experiment experiment);
    void deleteExperiment(Context context, Experiment experiment);
    void copyExperiment(Context context, Experiment experiment);

}
