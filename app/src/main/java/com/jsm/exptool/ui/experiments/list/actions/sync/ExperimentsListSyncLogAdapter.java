package com.jsm.exptool.ui.experiments.list.actions.sync;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;

import java.util.List;

public class ExperimentsListSyncLogAdapter extends BaseRecyclerAdapter<ExperimentSyncStateRow, ExperimentListSyncLogViewHolder> {

    public ExperimentsListSyncLogAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<ExperimentSyncStateRow>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public ExperimentListSyncLogViewHolder instanceViewHolder(View v) {
        return new ExperimentListSyncLogViewHolder(v);
    }
}
