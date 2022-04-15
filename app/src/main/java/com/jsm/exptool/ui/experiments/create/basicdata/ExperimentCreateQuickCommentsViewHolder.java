package com.jsm.exptool.ui.experiments.create.basicdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

public class ExperimentCreateQuickCommentsViewHolder extends BaseRecyclerViewHolder<String> {

    private final TextView titleTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<String> listener;
    protected ExperimentCreateQuickCommentsViewHolder(View v, DeleteActionListener<String> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.listener = listener;
    }


    @Override
    public void fillViewHolder(String element) {
        titleTV.setText(element);
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element, itemView.getContext());}});
    }
}
