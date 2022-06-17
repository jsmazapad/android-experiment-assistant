package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

public class BitrateSpinnerViewHolder extends BaseRecyclerViewHolder<Integer> {


    TextView titleTV;

    protected BitrateSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(android.R.id.text1);
    }

    @Override
    public void fillViewHolder(Integer element) {
        titleTV.setText(String.format("%d bps",element));
        titleTV.setPadding(10,20,10,20);

    }
}
