package com.jsm.exptool.ui.experiments.create.configure;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.entities.LocationOption;


import java.util.List;

public class LocationOptionsSpinnerAdapter extends BaseSpinnerAdapter<LocationOption, LocationOptionSpinnerViewHolder> {
    public LocationOptionsSpinnerAdapter(Context context, List<LocationOption> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public LocationOptionSpinnerViewHolder instanceViewHolder(View v) {
        return new LocationOptionSpinnerViewHolder(v);
    }
}
