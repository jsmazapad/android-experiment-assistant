package com.jsm.exptool.ui.experiments.create.basicdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;

public class ExperimentCreateBasicDataViewHolder extends BaseRecyclerViewHolder<SensorConfig> {

    private final TextView titleTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<SensorConfig> listener;
    protected ExperimentCreateBasicDataViewHolder(View v, DeleteActionListener<SensorConfig> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.listener = listener;
    }


    @Override
    public void fillViewHolder(SensorConfig element) {
        titleTV.setText(itemView.getContext().getString(element.getNameStringRes()));
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element, itemView.getContext());}});
    }
}
