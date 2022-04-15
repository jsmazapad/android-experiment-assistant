package com.jsm.exptool.ui.experiments.perform;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.List;

public class ExperimentPerformAdapter extends BaseRecyclerAdapter<SensorConfig, ExperimentPerformViewHolder> {

    public ExperimentPerformAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<SensorConfig>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);

    }

    @Override
    public ExperimentPerformViewHolder instanceViewHolder(View v) {
        return new ExperimentPerformViewHolder(v);
    }
}
