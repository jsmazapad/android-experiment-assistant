package com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewAdapter;

import java.util.List;

public class ImageRegisterGalleryAdapter extends MediaRegisterGalleryViewAdapter {

    public ImageRegisterGalleryAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<MediaRegister>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public ImageRegisterGalleryViewHolder instanceViewHolder(View v) {
        return new ImageRegisterGalleryViewHolder(v);
    }
}
