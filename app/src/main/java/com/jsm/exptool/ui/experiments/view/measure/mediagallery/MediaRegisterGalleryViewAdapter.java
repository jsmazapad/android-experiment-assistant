package com.jsm.exptool.ui.experiments.view.measure.mediagallery;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.register.MediaRegister;

import java.util.List;

public abstract class MediaRegisterGalleryViewAdapter extends BaseRecyclerAdapter<MediaRegister, MediaRegisterGalleryViewHolder> {

    public MediaRegisterGalleryViewAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<MediaRegister>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);

    }


}
