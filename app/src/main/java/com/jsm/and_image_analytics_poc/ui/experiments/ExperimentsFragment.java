package com.jsm.and_image_analytics_poc.ui.experiments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseActivity;
import com.jsm.and_image_analytics_poc.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.and_image_analytics_poc.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.and_image_analytics_poc.databinding.ExperimentsFragmentBinding;

public class ExperimentsFragment extends BaseRecyclerFragment<ExperimentsFragmentBinding, ExperimentsViewModel> {

    @Override
    protected ExperimentsFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiments_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentsAdapter(getContext(), viewModel, this, ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    protected ExperimentsViewModel getViewModel() {
        return new ViewModelProvider(this).get(ExperimentsViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiments_list_item;
    }
}
