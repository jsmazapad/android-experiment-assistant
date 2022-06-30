package com.jsm.exptool.ui.experiments.list;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;



import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.DialogLegendBinding;
import com.jsm.exptool.databinding.DialogSelectFrequencyBinding;
import com.jsm.exptool.databinding.ExperimentsListFragmentBinding;
import com.jsm.exptool.entities.eventbus.ExperimentListRefreshEvent;
import com.jsm.exptool.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class ExperimentsListFragment extends BaseRecyclerFragment<ExperimentsListFragmentBinding, ExperimentsListViewModel> {

    @Override
    protected ExperimentsListFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiments_list_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.info, menu);

        Drawable drawableInfo = menu.findItem(R.id.getInfo).getIcon();
        if (drawableInfo != null) {
            drawableInfo.mutate();
            drawableInfo.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.getInfo) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            DialogLegendBinding binding = DialogLegendBinding.inflate(layoutInflater);
            View mView = binding.getRoot();
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
            mBuilder.setView(mView);
            mBuilder.setPositiveButton(R.string.default_modal_okButton, null);
            mBuilder.create().show();
            return super.onOptionsItemSelected(item);
        }else{
            return ((MainActivity) requireActivity()).onOptionsItemSelected(item);
        }


    }
}
