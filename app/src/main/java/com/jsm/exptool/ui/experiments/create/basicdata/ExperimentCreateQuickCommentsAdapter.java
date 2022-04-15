package com.jsm.exptool.ui.experiments.create.basicdata;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;

import java.util.List;

public class ExperimentCreateQuickCommentsAdapter extends BaseRecyclerAdapter<String, ExperimentCreateQuickCommentsViewHolder> {
    DeleteActionListener<String> listener;

    public ExperimentCreateQuickCommentsAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<String>> elements, LifecycleOwner owner, NavController navController, int listItemResource, DeleteActionListener<String> listener) {
        super(context, onRecyclerItemSelectedListener, elements, owner, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ExperimentCreateQuickCommentsViewHolder instanceViewHolder(View v) {
        return new ExperimentCreateQuickCommentsViewHolder(v, listener);
    }
}
