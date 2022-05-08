package com.jsm.exptool.ui.experiments.list;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.AudioRecordingOption;
import com.jsm.exptool.model.filters.FilterOption;

public class ExperimentListFilterSpinnerViewHolder<FilterType, ValueType> extends BaseRecyclerViewHolder<FilterOption<FilterType, ValueType>> {


    TextView titleTV;

    protected ExperimentListFilterSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(android.R.id.text1);
    }

    @Override
    public void fillViewHolder(FilterOption<FilterType, ValueType> element) {
        titleTV.setText(element.getTitleTranslatableRes());
    }
}
