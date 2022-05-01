package com.jsm.exptool.ui.experiments.view;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;

public class ExperimentViewViewHolder extends BaseRecyclerViewHolder<RepeatableElementConfig> {

    private final TextView measuredItemTitleTV;
    protected ExperimentViewViewHolder(View v) {
        super(v);

        this.measuredItemTitleTV = v.findViewById(R.id.measuredItemTitleTV);

    }


    @Override
    public void fillViewHolder(RepeatableElementConfig element) {
        measuredItemTitleTV.setText(itemView.getContext().getString(element.getNameStringResource()));
    }
}
