package com.jsm.exptool.ui.experiments.list;

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.AudioRecordingOption;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.ui.experiments.create.audioconfiguration.AudioRecordingOptionsSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment> {

    MutableLiveData<List<ExperimentFilter>> filterOptions;


    public ExperimentsListViewModel(Application app) {
        super(app);
    }

    public MutableLiveData<List<ExperimentFilter>> getFilterOptions() {
        return filterOptions;
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        navController.navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavViewExperiment(elements.getValue().get(position)));
    }

    @Override
    public void setConstructorParameters(Object... args) {
        filterOptions = new MutableLiveData<>();
        filterOptions.setValue(getExperimentFilterStates());
    }

    @Override
    public void callRepositoryForData() {
        ExperimentsRepository.getExperiments(apiResponseRepositoryHolder);
    }

    public void onSelectedFilter(int position){

    }
    

    List <ExperimentFilter> getExperimentFilterStates(){
        return new ArrayList<ExperimentFilter>(){
            {
                add(new ExperimentFilter(Experiment.ExperimentStatus.CREATED, Experiment.ExperimentStatus.CREATED.status));
                add(new ExperimentFilter(Experiment.ExperimentStatus.INITIATED, Experiment.ExperimentStatus.INITIATED.status));
                add(new ExperimentFilter(Experiment.ExperimentStatus.FINISHED, Experiment.ExperimentStatus.FINISHED.status));
                add(new ExperimentFilter(null, null));
            }
        };
    }

    public class ExperimentFilter{
        private final Experiment.ExperimentStatus status;
        private final String filter;

        public ExperimentFilter(@Nullable Experiment.ExperimentStatus status, String filter) {
            this.status = status;
            this.filter = filter;
        }

        public Experiment.ExperimentStatus getStatus() {
            return status;
        }

        public String getFilter() {
            return filter;
        }

        @Override
        public String toString() {
            String statusString = "";
            switch (status){
                case CREATED:
                    statusString = "Creado";
                    break;
                case INITIATED:
                    statusString = "Iniciado";
                    break;
                case FINISHED:
                    statusString = "Finalizado";
                    break;
                default:
                    statusString = "Todos";
                    break;

            }
            return statusString;
        }
    }

}
