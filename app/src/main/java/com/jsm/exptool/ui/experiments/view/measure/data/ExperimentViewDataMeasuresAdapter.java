package com.jsm.exptool.ui.experiments.view.measure.data;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.List;

public class ExperimentViewDataMeasuresAdapter extends BaseRecyclerAdapter<ExperimentRegister, ExperimentViewDataMeasureViewHolder> {

    public ExperimentViewDataMeasuresAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<ExperimentRegister>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public ExperimentViewDataMeasureViewHolder instanceViewHolder(View v) {
        return new ExperimentViewDataMeasureViewHolder(v);
    }
}
