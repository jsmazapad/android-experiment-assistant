package com.jsm.exptool.ui.experiments.perform;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.MySensor;

import java.util.Map;

public class ExperimentPerformViewHolder extends BaseRecyclerViewHolder<MySensor> {

    private final TextView sensorTitleTV, valuesTV;
    protected ExperimentPerformViewHolder(View v) {
        super(v);

        this.sensorTitleTV = v.findViewById(R.id.sensorTitleTV);
        this.valuesTV = v.findViewById(R.id.valuesTV);
    }


    @Override
    public void fillViewHolder(MySensor element) {
        sensorTitleTV.setText(itemView.getContext().getString(element.getNameStringResource()));
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Float> entry: element.getMeasure().entrySet()){
            if (entry.getKey() != null && !"".equals(entry.getKey()))
            builder.append(entry.getKey() + String.format( ": %2f ", entry.getValue()));
        }
        valuesTV.setText(builder.toString());


    }
}
