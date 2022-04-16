package com.jsm.exptool.ui.configuration.suggestions;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;


import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ConfigurationSuggestionsFragmentBinding;

public class ConfigurationSuggestionsFragment extends BaseRecyclerFragment<ConfigurationSuggestionsFragmentBinding, ConfigurationSuggestionsViewModel> {


    @Override
    protected ConfigurationSuggestionsFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_suggestions_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ConfigurationSuggestionsAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ConfigurationSuggestionsViewModel createViewModel() {
        return new ViewModelProvider(this).get(ConfigurationSuggestionsViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.configuration_suggestions_list_element;
    }
}