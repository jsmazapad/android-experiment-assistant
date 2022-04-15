package com.jsm.exptool.ui.configuration.quickComments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ConfigurationSuggestionsFragmentBinding;

public class ConfigurationQuickCommentsFragment extends BaseRecyclerFragment<ConfigurationSuggestionsFragmentBinding, ConfigurationQuickCommentsViewModel> {


    @Override
    protected ConfigurationSuggestionsFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_suggestions_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ConfigurationQuickCommentsAdapter(getContext(), viewModel, getViewLifecycleOwner(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ConfigurationQuickCommentsViewModel createViewModel() {
        return new ViewModelProvider(this).get(ConfigurationQuickCommentsViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.configuration_suggestions_list_element;
    }
}