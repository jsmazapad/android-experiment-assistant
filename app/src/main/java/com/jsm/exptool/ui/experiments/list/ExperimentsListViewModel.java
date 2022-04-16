package com.jsm.exptool.ui.experiments.list;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;

import androidx.appcompat.app.AlertDialog;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

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
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.ExperimentActionsInterface;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment> implements ExperimentActionsInterface {

    private List<String> stateFilterOptions;
    private List<String> syncFilterOptions;
    private Experiment.ExperimentStatus statusFilter;
    private Boolean syncPending;
    private Boolean embeddingPending;
    private Boolean exportPending;
    private WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
    private final MutableLiveData<String> zippedFilePath = new MutableLiveData<>();


    public ExperimentsListViewModel(Application app) {
        super(app);
    }

    public List<String> getStateFilterOptions() {
        return stateFilterOptions;
    }

    public List<String> getSyncFilterOptions() {
        return syncFilterOptions;
    }

    public MutableLiveData<String> getZippedFilePath() {
        return zippedFilePath;
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        ExperimentProvider.createActionsDialog(c, elements.getValue().get(position), this);
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
        } else if (filter.equals(context.getString(R.string.experiment_filter_name_pending_sync))) {
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

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        LiveData<List<WorkInfo>> exportsWorkInfo = orchestratorProvider.getWorkInfoByTag(EXPORT_REGISTERS);
        exportsWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                handleError(new BaseException("Ha habido un error con la exportación de datos", false), getApplication());
            }
        });

        LiveData<List<WorkInfo>> zipWorkInfo = orchestratorProvider.getWorkInfoByTag(ZIP_EXPORTED);
        zipWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                handleError(new BaseException("Ha habido un error con la exportación de datos", false), getApplication());
            } else if (orchestratorProvider.countSuccessWorks(workInfoList) > 0) {
                Data outputData = workInfoList.get(0).getOutputData();
                String filename = outputData.getString(FILE_NAME);
                zippedFilePath.setValue(filename);

            }
        });
    }

    @Override
    public void initExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavPerformExperiment(experiment));
    }

    @Override
    public void viewExperimentData(Context context, Experiment experiment, AlertDialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavViewExperiment(experiment));

    }

    @Override
    public void exportExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        orchestratorProvider.executeExportToCSV(experiment);
    }

    @Override
    public void syncExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

    }

    @Override
    public void endExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

        if (ExperimentProvider.endExperiment(experiment) > 0) {
            ModalMessage.showSuccessfulOperation(context, null);
            callRepositoryForData();
            alertDialog.cancel();

        } else {
            ModalMessage.showFailureOperation(context, null);
        }
    }

    @Override
    public void continueExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

    }

    @Override
    public void deleteExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

    }

    @Override
    public void copyExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

    }

    public void shareZipped(Context context, String fileName) {
        Uri path = FileProvider.getUriForFile(context, "com.jsm.exptool.fileprovider", new File(fileName));
        ShareCompat.IntentBuilder intentBuilder = new ShareCompat.IntentBuilder(context).setStream(path).setChooserTitle("Almacenar en").setType("*/*");
        intentBuilder.startChooser();
    }
}
