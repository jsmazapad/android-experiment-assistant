package com.jsm.exptool.ui.experiments.list.actions.sync;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

public class ExperimentListSyncLogViewHolder extends BaseRecyclerViewHolder<ExperimentSyncStateRow> {
    TextView titleTV,resultTV;
    ImageView resultIV;

    protected ExperimentListSyncLogViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        resultTV = v.findViewById(R.id.resultTV);
        resultIV = v.findViewById(R.id.resultIV);

    }

    @Override
    public void fillViewHolder(ExperimentSyncStateRow element) {
        titleTV.setText(element.getTypeTitle());
        resultTV.setText(element.getResultExplanation());
        resultIV.setImageDrawable(AppCompatResources.getDrawable(itemView.getContext(), element.isSuccessful()? R.drawable.ic_baseline_check_24: R.drawable.ic_baseline_close_24));



    }
}
