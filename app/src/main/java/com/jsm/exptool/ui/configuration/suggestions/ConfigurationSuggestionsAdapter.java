package com.jsm.exptool.ui.configuration.suggestions;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;
import com.jsm.exptool.entities.CommentSuggestion;

import java.util.List;

public class ConfigurationSuggestionsAdapter extends BaseRecyclerAdapter<CommentSuggestion, ConfigurationSuggestionViewHolder> {
    DeleteActionListener<CommentSuggestion> listener;

    public ConfigurationSuggestionsAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<CommentSuggestion>> elements, NavController navController, int listItemResource, DeleteActionListener<CommentSuggestion> listener) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
        this.listener = listener;
    }

    @Override
    public ConfigurationSuggestionViewHolder instanceViewHolder(View v) {
        return new ConfigurationSuggestionViewHolder(v, listener);
    }
}
