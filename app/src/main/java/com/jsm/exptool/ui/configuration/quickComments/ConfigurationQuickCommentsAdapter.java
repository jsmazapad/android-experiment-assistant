package com.jsm.exptool.ui.configuration.quickComments;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.model.CommentSuggestion;

public class ConfigurationQuickCommentsAdapter extends BaseRecyclerAdapter<CommentSuggestion, ConfigurationQuickCommentsViewHolder> {
    DeleteActionListener listener;
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ConfigurationQuickCommentsAdapter(Context context, BaseRecyclerViewModelListener viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource, DeleteActionListener listener) {
        super(context, viewModel, viewModel.getElements(), navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ConfigurationQuickCommentsViewHolder instanceViewHolder(View v) {
        return new ConfigurationQuickCommentsViewHolder(v, listener);
    }
}
