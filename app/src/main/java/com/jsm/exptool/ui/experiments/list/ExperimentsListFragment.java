package com.jsm.exptool.ui.experiments.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentsListFragmentBinding;

public class ExperimentsListFragment extends BaseRecyclerFragment<ExperimentsListFragmentBinding, ExperimentsListViewModel> {

    @Override
    protected ExperimentsListFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiments_list_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentsListAdapter(getContext(), viewModel, this, ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    protected ExperimentsListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ExperimentsListViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiments_list_item;
    }
}
