package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;

import java.util.List;

public class BitrateSpinnerAdapter extends BaseSpinnerAdapter<Integer, BitrateSpinnerViewHolder> {
    public BitrateSpinnerAdapter(Context context, List<Integer> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public BitrateSpinnerViewHolder instanceViewHolder(View v) {
        return new BitrateSpinnerViewHolder(v);
    }
}
