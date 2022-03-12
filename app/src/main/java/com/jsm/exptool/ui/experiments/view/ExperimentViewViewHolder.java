package com.jsm.exptool.ui.experiments.view;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.Map;

public class ExperimentViewViewHolder extends BaseRecyclerViewHolder<RepeatableElement> {

    private final TextView measuredItemTitleTV;
    protected ExperimentViewViewHolder(View v) {
        super(v);

        this.measuredItemTitleTV = v.findViewById(R.id.measuredItemTitleTV);

    }


    @Override
    public void fillViewHolder(RepeatableElement element) {
        measuredItemTitleTV.setText(itemView.getContext().getString(element.getNameStringResource()));
    }
}
