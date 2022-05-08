package com.jsm.exptool.ui.experiments.list;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.model.filters.FilterOption;


import java.util.List;

public class ExperimentListFiltersSpinnerAdapter<FilterType, ValueType> extends BaseSpinnerAdapter<FilterOption<FilterType, ValueType>, ExperimentListFilterSpinnerViewHolder<FilterType, ValueType>> {
    public ExperimentListFiltersSpinnerAdapter(Context context, List<FilterOption<FilterType, ValueType>> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public ExperimentListFilterSpinnerViewHolder<FilterType, ValueType> instanceViewHolder(View v) {
        return new ExperimentListFilterSpinnerViewHolder<>(v);
    }
}
