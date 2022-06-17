package com.jsm.exptool.ui.experiments.create.configure;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.LocationOption;


public class LocationOptionSpinnerViewHolder extends BaseRecyclerViewHolder<LocationOption> {


    TextView titleTV;
    TextView descriptionTV;

    protected LocationOptionSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        descriptionTV = v.findViewById(R.id.descriptionTV);
    }

    @Override
    public void fillViewHolder(LocationOption element) {
        titleTV.setText(element.getTitleTranslatableRes());
        descriptionTV.setText(element.getDescriptionTranslatableRes());

    }
}
