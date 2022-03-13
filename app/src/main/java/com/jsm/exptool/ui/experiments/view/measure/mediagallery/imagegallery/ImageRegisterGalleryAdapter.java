package com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewAdapter;

public class ImageRegisterGalleryAdapter extends MediaRegisterGalleryViewAdapter<ImageRegisterGalleryViewHolder> {
    /**
     * @param context
     * @param viewModel        ViewModel asociado al fragment donde se incluye el recyclerView
     * @param lifeCycleOwner   Propietario del ciclo de vida
     * @param navController    Controlador de navegación (Android jetpack)
     * @param listItemResource Recurso layout donde se incluye la vista de cada item del recycler
     */
    public ImageRegisterGalleryAdapter(Context context, BaseRecyclerViewModel viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super(context, viewModel, lifeCycleOwner, navController, listItemResource);
    }

    @Override
    public ImageRegisterGalleryViewHolder instanceViewHolder(View v) {
        return new ImageRegisterGalleryViewHolder(v);
    }
}
