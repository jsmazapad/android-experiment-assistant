package com.jsm.exptool.ui.configuration;

import android.content.Context;
import android.view.View;

import com.jsm.exptool.core.ui.views.adapters.BaseSpinnerAdapter;
import com.jsm.exptool.entities.QuickCommentsCollection;
import com.jsm.exptool.providers.RemoteSyncMethodsProvider;
import com.jsm.exptool.ui.experiments.create.basicdata.QuickCommentsCollectionsSpinnerViewHolder;

import java.util.List;

public class RemoteSyncMethodsSpinnerAdapter extends BaseSpinnerAdapter<RemoteSyncMethodsProvider.RemoteConfigMethods, RemoteSyncMethodsSpinnerViewHolder> {
    public RemoteSyncMethodsSpinnerAdapter(Context context, List<RemoteSyncMethodsProvider.RemoteConfigMethods> list, int listItemResource) {
        super(context, list, listItemResource);
    }

    @Override
    public RemoteSyncMethodsSpinnerViewHolder instanceViewHolder(View v) {
        return new RemoteSyncMethodsSpinnerViewHolder(v);
    }
}
