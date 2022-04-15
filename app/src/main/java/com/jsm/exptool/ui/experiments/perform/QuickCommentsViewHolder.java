package com.jsm.exptool.ui.experiments.perform;

import android.view.View;
import android.widget.Button;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

public class QuickCommentsViewHolder extends BaseRecyclerViewHolder<String> {

    Button addCommentButton;

    protected QuickCommentsViewHolder(View v) {
        super(v);
        addCommentButton = v.findViewById(R.id.addCommentButton);
    }

    @Override
    public void fillViewHolder(String element) {
        addCommentButton.setText(element);
        addCommentButton.setContentDescription(addCommentButton.getContentDescription() + " " + element);
    }
}
