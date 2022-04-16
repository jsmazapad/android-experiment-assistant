package com.jsm.exptool.ui.experiments.create.basicdata;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.model.QuickCommentsCollection;


import java.util.List;

public class QuickCommentsCollectionsSpinnerAdapter extends BaseSpinnerAdapter<QuickCommentsCollection, QuickCommentsCollectionsSpinnerViewHolder> {
    public QuickCommentsCollectionsSpinnerAdapter(Context context, List<QuickCommentsCollection> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public QuickCommentsCollectionsSpinnerViewHolder instanceViewHolder(View v) {
        return new QuickCommentsCollectionsSpinnerViewHolder(v);
    }
}
