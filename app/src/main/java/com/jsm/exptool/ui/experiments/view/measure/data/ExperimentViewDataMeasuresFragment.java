package com.jsm.exptool.ui.experiments.view.measure.data;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentViewDataMeasuresFragmentBinding;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.ExperimentViewRegistersFragment;

import java.util.List;


public class ExperimentViewDataMeasuresFragment extends BaseRecyclerFragment<ExperimentViewDataMeasuresFragmentBinding, ExperimentViewDataMeasuresViewModel>{



    private static final String TAG = "MeasuresData";


    @Override
    protected ExperimentViewDataMeasuresFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_data_measures_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentViewDataMeasuresAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)this.getActivity()).getNavController(),getListItemResourceId());
    }

    @Override
    protected ExperimentViewDataMeasuresViewModel createViewModel() {

        RepeatableElementConfig sensor = ((ExperimentViewRegistersFragment)getParentFragment()).getViewModel().getMeasurableItem();

        List<ExperimentRegister> registers =((ExperimentViewRegistersFragment)getParentFragment()).getViewModel().getElements().getValue();

        return new ViewModelProvider(this, new ExperimentViewSensorMeasuresViewModelFactory(getActivity().getApplication(), sensor, registers)).get(ExperimentViewDataMeasuresViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_data_measure_list_item;
    }
}
