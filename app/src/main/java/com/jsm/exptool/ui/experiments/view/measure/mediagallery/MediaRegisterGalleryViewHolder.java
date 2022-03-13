package com.jsm.exptool.ui.experiments.view.measure.mediagallery;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.core.utils.DateUtils;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.providers.DateProvider;

public class MediaRegisterGalleryViewHolder extends BaseRecyclerViewHolder<MediaRegister> {

    protected final TextView nameTV;

    public MediaRegisterGalleryViewHolder(View v) {
        super(v);

        this.nameTV = v.findViewById(R.id.nameTV);

    }


    @Override
    public void fillViewHolder(MediaRegister element) {
        nameTV.setText(DateProvider.dateToDisplayStringWithTime(element.getDate()));
    }
}
