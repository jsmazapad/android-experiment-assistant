package com.jsm.exptool.ui.experiments.view.measure.sensor.data;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentViewSensorMeasuresFragmentBinding;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.repositories.SensorsRepository;


public class ExperimentViewSensorMeasuresFragment extends BaseRecyclerFragment<ExperimentViewSensorMeasuresFragmentBinding, ExperimentViewSensorMeasuresViewModel>{



    private static final String TAG = "MeasuresData";


    @Override
    protected ExperimentViewSensorMeasuresFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_registers_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentViewSensorMeasuresAdapter(getContext(), viewModel, getViewLifecycleOwner(), ((BaseActivity)this.getActivity()).getNavController(),getListItemResourceId());
    }

    @Override
    protected ExperimentViewSensorMeasuresViewModel createViewModel() {
        //TODO Código prueba, borrar
        MySensor sensor = SensorsRepository.getSensors().get(0);
        long experimentId = 1;

        return new ViewModelProvider(this, new ExperimentViewSensorViewModelFactory(getActivity().getApplication(), sensor, experimentId)).get(ExperimentViewSensorMeasuresViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_sensor_measure_list_item;
    }
}
