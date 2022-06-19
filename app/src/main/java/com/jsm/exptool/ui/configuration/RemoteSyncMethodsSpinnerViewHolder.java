package com.jsm.exptool.ui.configuration;

import android.view.View;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.providers.RemoteSyncMethodsProvider;

public class RemoteSyncMethodsSpinnerViewHolder extends BaseRecyclerViewHolder<RemoteSyncMethodsProvider.RemoteConfigMethods> {


    TextView titleTV;
    TextView descriptionTV;

    protected RemoteSyncMethodsSpinnerViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        descriptionTV = v.findViewById(R.id.descriptionTV);
    }

    @Override
    public void fillViewHolder(RemoteSyncMethodsProvider.RemoteConfigMethods element) {
        titleTV.setText(itemView.getContext().getString(element.getTitleStringRes()));
        descriptionTV.setText(itemView.getContext().getString(element.getDescriptionStringRes()));

    }
}
