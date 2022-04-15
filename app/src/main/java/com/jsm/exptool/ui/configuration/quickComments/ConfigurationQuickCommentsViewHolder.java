package com.jsm.exptool.ui.configuration.quickComments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.QuickCommentsCollection;

public class ConfigurationQuickCommentsViewHolder extends BaseRecyclerViewHolder<QuickCommentsCollection> {
    private final TextView titleTV, numUsedTimesTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<QuickCommentsCollection> listener;

    protected ConfigurationQuickCommentsViewHolder(View v, DeleteActionListener<QuickCommentsCollection> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.numUsedTimesTV = v.findViewById(R.id.numUsedTimesTV);
        this.listener = listener;
    }

    @Override
    public void fillViewHolder(QuickCommentsCollection element) {
        titleTV.setText(element.getName());
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element,  itemView.getContext());}});
        deleteIV.setContentDescription(deleteIV.getContentDescription() + element.getComment());
    }
}
