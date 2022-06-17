package com.jsm.exptool.ui.experiments.list;

import android.content.Context;
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
import com.jsm.exptool.entities.eventbus.ExperimentListRefreshEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ExperimentsListFragment extends BaseRecyclerFragment<ExperimentsListFragmentBinding, ExperimentsListViewModel> {

    @Override
    protected ExperimentsListFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiments_list_fragment, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ExperimentListRefreshEvent event) {
        if(event.isRefreshNeeded()){
            this.viewModel.callRepositoryForData();
        }
        // Do something
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentsListAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    protected ExperimentsListViewModel createViewModel() {
        //TODO Código desarrollo
        //MockExamples.registerFullExperiment();
        return new ViewModelProvider(this).get(ExperimentsListViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiments_list_item;
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.getZippedFilePath().observe(getViewLifecycleOwner(), value ->{
            if(value != null){
                viewModel.shareZipped(getContext(), value);
            }
        });
    }
}
