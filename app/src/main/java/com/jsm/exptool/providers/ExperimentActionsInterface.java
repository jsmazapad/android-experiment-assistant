package com.jsm.exptool.providers;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.jsm.exptool.model.Experiment;

public interface ExperimentActionsInterface {

    void initExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void viewExperimentData(Context context, Experiment experiment, AlertDialog alertDialog);
    void exportExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void syncExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void endExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void continueExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void deleteExperiment(Context context, Experiment experiment, AlertDialog alertDialog);
    void copyExperiment(Context context, Experiment experiment, AlertDialog alertDialog);

}
