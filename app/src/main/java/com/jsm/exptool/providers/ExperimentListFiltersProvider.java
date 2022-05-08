package com.jsm.exptool.providers;

import com.jsm.exptool.R;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.filters.FilterOption;

import java.util.ArrayList;
import java.util.List;

public class ExperimentListFiltersProvider {

    public enum ConditionFilterOptions {
        SYNC,
        EMBEDDING,
        EXPORTED
    }

    public static List<FilterOption<Experiment.ExperimentStatus, Boolean>> getExperimentFilterStates() {
        return new ArrayList<FilterOption<Experiment.ExperimentStatus, Boolean>>() {
            {
                add(new FilterOption<>(R.string.experiment_filter_name_all, null, false));
                add(new FilterOption<>(R.string.experiment_filter_name_create, Experiment.ExperimentStatus.CREATED,true));
                add(new FilterOption<>(R.string.experiment_filter_name_init,  Experiment.ExperimentStatus.INITIATED,true));
                add(new FilterOption<>(R.string.experiment_filter_name_finished,  Experiment.ExperimentStatus.FINISHED,true));
            }
        };
    }

    public static List<FilterOption<ConditionFilterOptions, Boolean>> getSyncFilterStates() {
        return new ArrayList<FilterOption<ConditionFilterOptions, Boolean>>() {
            {
                add(new FilterOption<>(R.string.experiment_filter_name_all, null, false ));
                add(new FilterOption<>(R.string.experiment_filter_name_pending_sync, ConditionFilterOptions.SYNC, true ));
                add(new FilterOption<>(R.string.experiment_filter_name_complete_sync, ConditionFilterOptions.SYNC, false ));
                add(new FilterOption<>(R.string.experiment_filter_name_pending_embedding, ConditionFilterOptions.EMBEDDING, true ));
                add(new FilterOption<>(R.string.experiment_filter_name_complete_embedding, ConditionFilterOptions.EMBEDDING, false ));
                add(new FilterOption<>(R.string.experiment_filter_name_pending_exported, ConditionFilterOptions.EXPORTED, true ));
                add(new FilterOption<>(R.string.experiment_filter_name_exported, ConditionFilterOptions.EXPORTED, false ));
            }
        };
    }


}
