package com.jsm.exptool.ui.configuration.quickComments.addquickcomments;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.ui.configuration.suggestions.ConfigurationSuggestionViewHolder;

import java.util.List;

public class ConfigurationAddQuickCommentsCollectionAdapter extends BaseRecyclerAdapter<String, ConfigurationAddQuickCommentsCollectionViewHolder> {
    DeleteActionListener<String> listener;

    public ConfigurationAddQuickCommentsCollectionAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<String>> elements, NavController navController, int listItemResource, DeleteActionListener<String> listener) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ConfigurationAddQuickCommentsCollectionViewHolder instanceViewHolder(View v) {
        return new ConfigurationAddQuickCommentsCollectionViewHolder(v, listener);
    }
}
