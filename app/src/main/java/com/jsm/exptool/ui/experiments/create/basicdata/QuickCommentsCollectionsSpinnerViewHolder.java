package com.jsm.exptool.ui.experiments.create.basicdata;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.AudioRecordingOption;
import com.jsm.exptool.model.QuickCommentsCollection;

public class QuickCommentsCollectionsSpinnerViewHolder extends BaseRecyclerViewHolder<QuickCommentsCollection> {


    TextView titleTV;
    TextView descriptionTV;

    protected QuickCommentsCollectionsSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        descriptionTV = v.findViewById(R.id.descriptionTV);
    }

    @Override
    public void fillViewHolder(QuickCommentsCollection element) {
        titleTV.setText(element.getName());
        descriptionTV.setText(TextUtils.join(", ", element.getQuickComments()));

    }
}
