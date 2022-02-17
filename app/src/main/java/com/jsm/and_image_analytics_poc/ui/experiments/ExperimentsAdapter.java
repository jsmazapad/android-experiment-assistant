package com.jsm.and_image_analytics_poc.ui.experiments;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.and_image_analytics_poc.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.and_image_analytics_poc.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.and_image_analytics_poc.model.Experiment;

public class ExperimentsAdapter extends BaseRecyclerAdapter<Experiment, ExperimentViewHolder, Experiment> {
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ExperimentsAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);
    }

    @Override
    public ExperimentViewHolder instanceViewHolder(View v) {
        return new ExperimentViewHolder(v);
    }
}
