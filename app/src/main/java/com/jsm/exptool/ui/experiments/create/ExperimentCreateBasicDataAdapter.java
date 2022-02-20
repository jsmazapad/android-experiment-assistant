package com.jsm.exptool.ui.experiments.create;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;

public class ExperimentCreateBasicDataAdapter extends BaseRecyclerAdapter<MySensor, ExperimentCreateBasicDataViewHolder, MySensor> {
    DeleteActionListener listener;
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ExperimentCreateBasicDataAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource, DeleteActionListener listener) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ExperimentCreateBasicDataViewHolder instanceViewHolder(View v) {
        return new ExperimentCreateBasicDataViewHolder(v, listener);
    }
}
