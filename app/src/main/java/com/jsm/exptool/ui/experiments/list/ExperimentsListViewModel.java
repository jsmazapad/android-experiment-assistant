package com.jsm.exptool.ui.experiments.list;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.util.List;

public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment> {


    public ExperimentsListViewModel(Application app) {
        super(app);
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
        ExperimentsRepository.getExperiments(apiResponseRepositoryHolder);
    }
}
