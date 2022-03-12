package com.jsm.exptool.ui.experiments.view.measure.graph;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentViewSensorGraphFragmentBinding;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.ExperimentViewRegistersFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentViewSensorGraphFragment extends BaseFragment<ExperimentViewSensorGraphFragmentBinding, ExperimentViewSensorGraphViewModel> {



    private boolean isLoaded = false;

    private ImageView imageView;
    //private Animatable animatable;
    private LinearLayout loading;

    private static final String TAG = "MeasuresGraph";
    @Override
    protected ExperimentViewSensorGraphViewModel createViewModel() {
        return new ViewModelProvider(this).get(ExperimentViewSensorGraphViewModel.class);
    }

    @Override
    protected ExperimentViewSensorGraphFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_sensor_graph_fragment, container, false);
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        RepeatableElement measurableItem = ((ExperimentViewRegistersFragment) getParentFragment()).getViewModel().getMeasurableItem();
        if(!(measurableItem instanceof MySensor))
        {
            return;
        }

        MySensor sensor = (MySensor) measurableItem;

        List<ExperimentRegister> registers = ((ExperimentViewRegistersFragment) getParentFragment()).getViewModel().getElements().getValue();

        loading = (LinearLayout) binding.getRoot().findViewById(R.id.loading);
        imageView = loading.findViewById(R.id.loading_logo);
        //animatable = (Animatable) imageView.getDrawable();

        LineChart graph = (LineChart) binding.getRoot().findViewById(R.id.lineChart);
        //graph.setDragEnabled(true);
        //graph.setScaleEnabled(false);
        ArrayList<ExperimentRegister> reversedArrayList = new ArrayList<ExperimentRegister>() {{
            addAll(registers);
        }};
        Collections.reverse(reversedArrayList);
        new GraphHelper(graph,reversedArrayList);
    }







}
