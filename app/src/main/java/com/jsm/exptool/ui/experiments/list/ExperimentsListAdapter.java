package com.jsm.exptool.ui.experiments.list;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.List;

public class ExperimentsListAdapter extends BaseRecyclerAdapter<Experiment, ExperimentListViewHolder> {

    public ExperimentsListAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<Experiment>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public ExperimentListViewHolder instanceViewHolder(View v) {
        return new ExperimentListViewHolder(v);
    }
}
