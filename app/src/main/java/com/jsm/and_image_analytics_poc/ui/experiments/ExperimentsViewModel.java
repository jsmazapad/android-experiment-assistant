package com.jsm.and_image_analytics_poc.ui.experiments;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ListResponse;
import com.jsm.and_image_analytics_poc.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.and_image_analytics_poc.model.Experiment;
import com.jsm.and_image_analytics_poc.repositories.ExperimentsRepository;

import java.util.List;

public class ExperimentsViewModel extends BaseRecyclerViewModel<Experiment, Experiment> {


    public ExperimentsViewModel(Application app) {
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
