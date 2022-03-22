package com.jsm.exptool.ui.experiments.list;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.util.ArrayList;
import java.util.List;

public class ExperimentsDialogActionsViewModel extends BaseViewModel{



    public ExperimentsDialogActionsViewModel(Application app) {
        super(app);
    }




}
