package com.jsm.exptool.ui.experiments.create.cameraconfiguration;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.model.embedding.EmbeddingAlgorithm;

import java.util.List;

public class EmbeddingAlgorithmsSpinnerAdapter extends BaseSpinnerAdapter<EmbeddingAlgorithm, EmbeddingAlgorithmSpinnerViewHolder> {
    public EmbeddingAlgorithmsSpinnerAdapter(Context context, List<EmbeddingAlgorithm> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public EmbeddingAlgorithmSpinnerViewHolder instanceViewHolder(View v) {
        return new EmbeddingAlgorithmSpinnerViewHolder(v);
    }
}
