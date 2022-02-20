package com.jsm.exptool.ui.experiments.create.basicdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.MySensor;

public class ExperimentCreateBasicDataViewHolder extends BaseRecyclerViewHolder<MySensor> {

    private final TextView titleTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<MySensor> listener;
    protected ExperimentCreateBasicDataViewHolder(View v, DeleteActionListener<MySensor> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.listener = listener;
    }


    @Override
    public void fillViewHolder(MySensor element) {
        titleTV.setText(itemView.getContext().getString(element.getRName()));
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element);}});
    }
}
