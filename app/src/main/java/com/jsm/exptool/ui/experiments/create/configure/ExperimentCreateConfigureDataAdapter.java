package com.jsm.exptool.ui.experiments.create.configure;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.SensorConfig;


import java.util.List;

public class ExperimentCreateConfigureDataAdapter extends BaseRecyclerAdapter<SensorConfig, ExperimentCreateConfigureDataViewHolder> {

    public ExperimentCreateConfigureDataAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<SensorConfig>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public ExperimentCreateConfigureDataViewHolder instanceViewHolder(View v) {
        return new ExperimentCreateConfigureDataViewHolder(v);
    }
}
