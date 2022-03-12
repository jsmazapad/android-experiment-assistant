package com.jsm.exptool.ui.experiments.view.measure.imagegallery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ImageRegister;

public class ImageRegisterGalleryViewHolder extends BaseRecyclerViewHolder<ImageRegister> {

    private final TextView nameTV;
    private final ImageView thumbnailIV;
    protected ImageRegisterGalleryViewHolder(View v) {
        super(v);

        this.nameTV = v.findViewById(R.id.nameTV);
        this.thumbnailIV = v.findViewById(R.id.thumbnailIV);

    }


    @Override
    public void fillViewHolder(ImageRegister element) {
        nameTV.setText(element.getFileName());
    }
}
