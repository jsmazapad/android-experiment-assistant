package com.jsm.exptool.ui.experiments.list.actions.sync;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;

import com.jsm.exptool.databinding.ExperimentsListSyncLogFragmentBinding;
import com.jsm.exptool.entities.Experiment;



public class ExperimentsListSyncLogFragment extends BaseRecyclerFragment<ExperimentsListSyncLogFragmentBinding, ExperimentsListSyncLogViewModel> {

    @Override
    protected ExperimentsListSyncLogFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiments_list_sync_log_fragment, container, false);
    }


    @Override
    protected ExperimentsListSyncLogAdapter createAdapter() {
        return new ExperimentsListSyncLogAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    protected ExperimentsListSyncLogViewModel createViewModel() {
        //TODO Código desarrollo
        //MockExamples.registerFullExperiment();
        Experiment experiment = ExperimentsListSyncLogFragmentArgs.fromBundle(getArguments()).getExperiment();
        return new ViewModelProvider(this, new ExperimentsListSyncLogViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentsListSyncLogViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiments_list_sync_log_item;
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.getRowAdded().observe(getViewLifecycleOwner(), position->{
            if (position != null) {
                recyclerView.getAdapter().notifyItemInserted(position);
            }
        });

    }
}
