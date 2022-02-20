package com.jsm.exptool.ui.experiments.list;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;

public class ExperimentsListAdapter extends BaseRecyclerAdapter<Experiment, ExperimentListViewHolder, Experiment> {
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ExperimentsListAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);
    }

    @Override
    public ExperimentListViewHolder instanceViewHolder(View v) {
        return new ExperimentListViewHolder(v);
    }
}
