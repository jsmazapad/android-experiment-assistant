package com.jsm.exptool.ui.experiments.view.measure.imagegallery;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.ui.experiments.view.ExperimentViewViewHolder;

public class ImageRegisterGalleryViewAdapter extends BaseRecyclerAdapter<ImageRegister, ImageRegisterGalleryViewHolder, ImageRegister> {
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ImageRegisterGalleryViewAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);

    }

    @Override
    public ImageRegisterGalleryViewHolder instanceViewHolder(View v) {
        return new ImageRegisterGalleryViewHolder(v);
    }
}
