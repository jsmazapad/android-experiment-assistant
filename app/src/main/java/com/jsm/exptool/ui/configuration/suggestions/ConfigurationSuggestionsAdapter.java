package com.jsm.exptool.ui.configuration.suggestions;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.ui.experiments.list.ExperimentListViewHolder;

public class ConfigurationSuggestionsAdapter extends BaseRecyclerAdapter<CommentSuggestion, ConfigurationSuggestionViewHolder, CommentSuggestion> {
    DeleteActionListener listener;
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ConfigurationSuggestionsAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource, DeleteActionListener listener) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ConfigurationSuggestionViewHolder instanceViewHolder(View v) {
        return new ConfigurationSuggestionViewHolder(v, listener);
    }
}
