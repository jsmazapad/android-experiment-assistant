package com.jsm.exptool.ui.experiments.create.basicdata;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.List;

public class ExperimentCreateBasicDataAdapter extends BaseRecyclerAdapter<SensorConfig, ExperimentCreateBasicDataViewHolder> {
    DeleteActionListener<SensorConfig> listener;

    public ExperimentCreateBasicDataAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<SensorConfig>> elements, NavController navController, int listItemResource, DeleteActionListener<SensorConfig> listener) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ExperimentCreateBasicDataViewHolder instanceViewHolder(View v) {
        return new ExperimentCreateBasicDataViewHolder(v, listener);
    }
}
