package com.jsm.exptool.ui.experiments.list;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.filters.FilterOption;
import com.jsm.exptool.providers.ExperimentListFiltersProvider;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.data.repositories.ExperimentsRepository;
import com.jsm.exptool.ui.experiments.list.actions.ExperimentListActionsDialogFragment;
import com.jsm.exptool.ui.splash.SplashActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//TODO Añadir total de Mb ocupado y restante a vista
public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment>  {


    private List<FilterOption<Experiment.ExperimentStatus, Boolean>> stateFilterOptions;
    private List<FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean>> conditionFilterOptions;
    private final MutableLiveData<String> filterResume = new MutableLiveData<>();
    private Experiment.ExperimentStatus statusFilterSelected;
    private FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean> conditionFilterSelected;
    private final WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
    private final MutableLiveData<String> zippedFilePath = new MutableLiveData<>();


    public ExperimentsListViewModel(Application app) {
        super(app);
        compoundFilterResumeText();
    }

    public List<FilterOption<Experiment.ExperimentStatus, Boolean>> getStateFilterOptions() {
        return stateFilterOptions;
    }

    public List<FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean>> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public MutableLiveData<String> getFilterResume() {
        return filterResume;
    }

    public ExperimentListFiltersSpinnerAdapter<Experiment.ExperimentStatus, Boolean> getStatusFilterAdapter(Context context) {
        return new ExperimentListFiltersSpinnerAdapter<>(context, stateFilterOptions, android.R.layout.simple_spinner_dropdown_item);
    }
    public ExperimentListFiltersSpinnerAdapter<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean> getConditionsFilterAdapter(Context context) {
        return new ExperimentListFiltersSpinnerAdapter<>(context, conditionFilterOptions, android.R.layout.simple_spinner_dropdown_item);
    }


    public MutableLiveData<String> getZippedFilePath() {
        return zippedFilePath;
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    private boolean blockedFlag = false;

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

        Experiment selectedExperiment = elements.getValue().get(position);
        if(!blockedFlag) {
            blockedFlag = true;
            openActionsDialog(c, selectedExperiment);
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                    blockedFlag = false;
            }, 800, TimeUnit.MILLISECONDS);

        }


    }


    @Override
    public void setConstructorParameters(Object... args) {

        stateFilterOptions = ExperimentListFiltersProvider.getExperimentFilterStates();
        conditionFilterOptions = ExperimentListFiltersProvider.getSyncFilterStates();


    }

    @Override
    public void callRepositoryForData() {
        ExperimentListFiltersProvider.ConditionFilterOptions conditionFilterSelectedVar = conditionFilterSelected != null ? conditionFilterSelected.getFilterVar() : null;
        boolean conditionFilterSelectedValue = conditionFilterSelected != null ? conditionFilterSelected.getFilterValue() : false;
        ExperimentsRepository.getExperiments(apiResponseRepositoryHolder, statusFilterSelected, conditionFilterSelectedVar , conditionFilterSelectedValue);
    }

    public void onSelectedStateFilter(int position) {
        if (stateFilterOptions.size() < position)
            return;
        statusFilterSelected = stateFilterOptions.get(position).getFilterVar();
        compoundFilterResumeText();
        callRepositoryForData();

    }

    public void onSelectedConditionFilter(int position) {
        if (conditionFilterOptions.size() < position)
            return;
        conditionFilterSelected = conditionFilterOptions.get(position);
        compoundFilterResumeText();
        callRepositoryForData();

    }

    public void compoundFilterResumeText(){
        StringBuilder resumeBuilder = new StringBuilder();
        if(statusFilterSelected != null){
            resumeBuilder.append(getApplication().getString(R.string.status_filter_text)).append(": ");
            resumeBuilder.append(ExperimentProvider.getTranslatableStringFromExperimentStatus(statusFilterSelected, getApplication()));
        }

        if(statusFilterSelected != null && conditionFilterSelected.getFilterVar() != null)
        {
            resumeBuilder.append(", ");
        }

        if(conditionFilterSelected != null && conditionFilterSelected.getFilterVar() != null){
            resumeBuilder.append(getApplication().getString(R.string.condition_filter_text)).append(": ");
            resumeBuilder.append(getApplication().getString(conditionFilterSelected.getTitleTranslatableRes()));
        }

        if("".equals( resumeBuilder.toString())){
            resumeBuilder.append(getApplication().getString(R.string.no_filters));
        }

        filterResume.setValue(resumeBuilder.toString());
    }




    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        //initExperimentActionsDialogObservers(owner);
        LiveData<List<WorkInfo>> exportsWorkInfo = orchestratorProvider.getWorkInfoByTag(EXPORT_REGISTERS);
        exportsWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                orchestratorProvider.finishJobsByTag(EXPORT_REGISTERS);
                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
            }
        });

        LiveData<List<WorkInfo>> zipWorkInfo = orchestratorProvider.getWorkInfoByTag(ZIP_EXPORTED);
        zipWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                if (!isShowingDialog()) {
                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
                }
            } else if (orchestratorProvider.countSuccessWorks(workInfoList) > 0) {
                Data outputData = workInfoList.get(0).getOutputData();
                String filename = outputData.getString(FILE_NAME);
                zippedFilePath.setValue(filename);

            }
        });
    }



    public void shareZipped(Context context, String fileName) {
        //TODO refactorizar authority
        Uri path = FileProvider.getUriForFile(context, "com.jsm.exptool.fileprovider", new File(fileName));
        ShareCompat.IntentBuilder intentBuilder = new ShareCompat.IntentBuilder(context).setStream(path).setChooserTitle(R.string.export_experiment_share_chooser_title).setType("*/*");
        intentBuilder.startChooser();
    }



    private void openActionsDialog(Context context, Experiment experiment) {
        ExperimentListActionsDialogFragment dialogFragment = ExperimentListActionsDialogFragment.newInstance(experiment);
        dialogFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "dialog");
    }

}
