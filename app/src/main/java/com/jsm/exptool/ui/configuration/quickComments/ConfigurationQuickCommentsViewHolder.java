package com.jsm.exptool.ui.configuration.quickComments;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.ItemManagementActionsListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.QuickCommentsCollection;

public class ConfigurationQuickCommentsViewHolder extends BaseRecyclerViewHolder<QuickCommentsCollection> {
    private final TextView titleTV, descriptionTV;
    private final ImageView deleteIV, editIV;
    private final ItemManagementActionsListener<QuickCommentsCollection> listener;

    protected ConfigurationQuickCommentsViewHolder(View v, ItemManagementActionsListener<QuickCommentsCollection> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.editIV = v.findViewById(R.id.editIV);
        this.descriptionTV = v.findViewById(R.id.descriptionTV);
        this.listener = listener;
    }

    @Override
    public void fillViewHolder(QuickCommentsCollection element) {
        titleTV.setText(element.getName());
        descriptionTV.setText(TextUtils.join(", ", element.getQuickComments()));
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element,  itemView.getContext());}});
        deleteIV.setContentDescription(deleteIV.getContentDescription() + element.getName());
        editIV.setOnClickListener(v -> {if(listener != null){listener.edit(element,  itemView.getContext());}});
        editIV.setContentDescription(editIV.getContentDescription() + element.getName());
    }
}
