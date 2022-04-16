package com.jsm.exptool.ui.configuration.quickComments;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.ItemManagementActionsListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.QuickCommentsCollection;

import java.util.List;

public class ConfigurationQuickCommentsAdapter extends BaseRecyclerAdapter<QuickCommentsCollection, ConfigurationQuickCommentsViewHolder> {
    ItemManagementActionsListener<QuickCommentsCollection> listener;

    public ConfigurationQuickCommentsAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<QuickCommentsCollection>> elements, NavController navController, int listItemResource, ItemManagementActionsListener<QuickCommentsCollection> listener) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ConfigurationQuickCommentsViewHolder instanceViewHolder(View v) {
        return new ConfigurationQuickCommentsViewHolder(v, listener);
    }
}
