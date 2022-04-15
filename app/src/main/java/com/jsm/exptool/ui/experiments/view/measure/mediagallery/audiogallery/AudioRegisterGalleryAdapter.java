package com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewAdapter;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewHolder;

import java.util.List;

public class AudioRegisterGalleryAdapter extends MediaRegisterGalleryViewAdapter {

    public AudioRegisterGalleryAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<MediaRegister>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public MediaRegisterGalleryViewHolder instanceViewHolder(View v) {
        return new MediaRegisterGalleryViewHolder(v);
    }
}
