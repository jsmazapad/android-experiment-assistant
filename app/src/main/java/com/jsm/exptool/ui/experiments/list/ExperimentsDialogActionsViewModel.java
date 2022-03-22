package com.jsm.exptool.ui.experiments.list;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.util.ArrayList;
import java.util.List;

public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment> {

    List<String> stateFilterOptions;
    List<String> syncFilterOptions;
    Experiment.ExperimentStatus statusFilter;
    Boolean syncPending;
    Boolean embeddingPending;
    Boolean exportPending;




    public ExperimentsListViewModel(Application app) {
        super(app);
    }

    public List<String> getStateFilterOptions() {
        return stateFilterOptions;
    }

    public List<String> getSyncFilterOptions() {
        return syncFilterOptions;
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        //navController.navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavViewExperiment(elements.getValue().get(position)));
        ExperimentProvider.createActionsDialog(c, elements.getValue().get(position));
    }

    @Override
    public void setConstructorParameters(Object... args) {

        stateFilterOptions = getExperimentFilterStates();
        syncFilterOptions = getSyncFilterStates();
    }

    @Override
    public void callRepositoryForData() {
        ExperimentsRepository.getExperiments(apiResponseRepositoryHolder, statusFilter);
    }

    public void onSelectedStateFilter(int position) {
        Context context = getApplication();
        if (stateFilterOptions.size() < position)
            return;
        String filter = stateFilterOptions.get(position);

        if (filter.equals(context.getString(R.string.experiment_filter_name_create))) {
            statusFilter = Experiment.ExperimentStatus.CREATED;
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_init))) {
            statusFilter = Experiment.ExperimentStatus.INITIATED;
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_finished))) {
            statusFilter = Experiment.ExperimentStatus.FINISHED;
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_all))) {
            statusFilter = null;
        }

        callRepositoryForData();

    }

    public void onSelectedSyncFilter(int position) {
        Context context = getApplication();
        if (syncFilterOptions.size() < position)
            return;
        String filter = syncFilterOptions.get(position);

        if (filter.equals(context.getString(R.string.experiment_filter_name_all))) {
            syncPending = null;
            embeddingPending = null;
            exportPending = null;
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_pending_embedding))) {
            syncPending = null;
            embeddingPending = true;
            exportPending = null;
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_pending_exported))) {
            syncPending = null;
            embeddingPending = null;
            exportPending = true;
        }else if (filter.equals(context.getString(R.string.experiment_filter_name_pending_sync))) {
            syncPending = true;
            embeddingPending = null;
            exportPending = null;
        }

        callRepositoryForData();

    }


    List<String> getExperimentFilterStates() {
        Context context = getApplication();
        return new ArrayList<String>() {
            {
                add(context.getString(R.string.experiment_filter_name_all));
                add(context.getString(R.string.experiment_filter_name_create));
                add(context.getString(R.string.experiment_filter_name_init));
                add(context.getString(R.string.experiment_filter_name_finished));
            }
        };
    }

    List<String> getSyncFilterStates() {
        Context context = getApplication();
        return new ArrayList<String>() {
            {
                add(context.getString(R.string.experiment_filter_name_all));
                add(context.getString(R.string.experiment_filter_name_pending_sync));
                add(context.getString(R.string.experiment_filter_name_pending_embedding));
                add(context.getString(R.string.experiment_filter_name_pending_exported));
            }
        };
    }


}
