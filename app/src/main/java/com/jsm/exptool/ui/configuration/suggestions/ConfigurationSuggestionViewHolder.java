package com.jsm.exptool.ui.configuration.suggestions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.entities.CommentSuggestion;

public class ConfigurationSuggestionViewHolder extends BaseRecyclerViewHolder<CommentSuggestion> {
    private final TextView titleTV, numUsedTimesTV;
    private final ImageView deleteIV;
    private final DeleteActionListener<CommentSuggestion> listener;

    protected ConfigurationSuggestionViewHolder(View v, DeleteActionListener<CommentSuggestion> listener) {
        super(v);

        this.titleTV = v.findViewById(R.id.titleTV);
        this.deleteIV = v.findViewById(R.id.deleteIV);
        this.numUsedTimesTV = v.findViewById(R.id.numUsedTimesTV);
        this.listener = listener;
    }

    @Override
    public void fillViewHolder(CommentSuggestion element) {
        titleTV.setText(element.getComment());
        numUsedTimesTV.setText(String.format(itemView.getContext().getString(R.string.used_n_times_format_string), element.getUsedTimesCounter()));
        deleteIV.setOnClickListener(v -> {if(listener != null){listener.delete(element,  itemView.getContext());}});
        deleteIV.setContentDescription(deleteIV.getContentDescription() + element.getComment());
    }
}
