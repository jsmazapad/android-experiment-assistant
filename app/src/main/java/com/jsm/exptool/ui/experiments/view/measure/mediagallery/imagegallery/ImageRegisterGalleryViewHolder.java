package com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery;

import android.view.View;
import android.widget.ImageView;

import com.jsm.exptool.R;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.providers.ImagesProvider;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewHolder;

public class ImageRegisterGalleryViewHolder extends MediaRegisterGalleryViewHolder{

    private final ImageView thumbnailIV;
    protected ImageRegisterGalleryViewHolder(View v) {
        super(v);
        this.thumbnailIV = v.findViewById(R.id.thumbnailIV);

    }


    @Override
    public void fillViewHolder(MediaRegister element) {
        nameTV.setText(element.getFileName());
        ImagesProvider.loadImageForListOrDetail(element.getFullPath() ,thumbnailIV, true );
    }
}
