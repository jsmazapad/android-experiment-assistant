package com.jsm.exptool.ui.configuration.quickComments.manage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

public class ConfigurationAddQuickCommentsCollectionViewHolder extends BaseRecyclerViewHolder<String> {
    private final TextView titleTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<String> listener;

    protected ConfigurationAddQuickCommentsCollectionViewHolder(View v, DeleteActionListener<String> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.listener = listener;
    }

    @Override
    public void fillViewHolder(String element) {
        titleTV.setText(element);
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element,  itemView.getContext());}});
        deleteIV.setContentDescription(deleteIV.getContentDescription() + element);
    }
}
