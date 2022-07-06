package com.jsm.exptool.ui.experiments.perform;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;

import java.util.Map;

public class ExperimentPerformViewHolder extends BaseRecyclerViewHolder<SensorConfig> {

    private final TextView sensorTitleTV, valuesTV;
    protected ExperimentPerformViewHolder(View v) {
        super(v);

        this.sensorTitleTV = v.findViewById(R.id.sensorTitleTV);
        this.valuesTV = v.findViewById(R.id.valuesTV);
    }


    @Override
    public void fillViewHolder(SensorConfig element) {
        sensorTitleTV.setText(itemView.getContext().getString(element.getNameStringRes()));
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Float> entry: element.getSensorReader().getMeasure().entrySet()){
            if (entry.getKey() != null && !"".equals(entry.getKey()))
            builder.append(entry.getKey() + String.format( ": %2f ", entry.getValue()));
        }
        valuesTV.setText(builder.toString());


    }
}
