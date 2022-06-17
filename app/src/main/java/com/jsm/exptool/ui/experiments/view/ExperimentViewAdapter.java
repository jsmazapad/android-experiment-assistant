package com.jsm.exptool.ui.experiments.view;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;

import java.util.List;

public class ExperimentViewAdapter extends BaseRecyclerAdapter<RepeatableElementConfig, ExperimentViewViewHolder> {

    public ExperimentViewAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<RepeatableElementConfig>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);

    }

    @Override
    public ExperimentViewViewHolder instanceViewHolder(View v) {
        return new ExperimentViewViewHolder(v);
    }
}
