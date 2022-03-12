package com.jsm.exptool.ui.experiments.view.measure;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.data.ExperimentViewDataMeasuresFragment;
import com.jsm.exptool.ui.experiments.view.measure.graph.ExperimentViewSensorGraphFragment;

import java.util.ArrayList;

public class MeasureSectionsPagerAdapter extends FragmentStateAdapter {


    private final ArrayList<ExperimentRegister> registerList;
    private final RepeatableElement measurableItem;
    private final static int NUM_PAGES = 2;

    public MeasureSectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<ExperimentRegister> registerList, RepeatableElement measurableItem) {
        super(fragmentManager, lifecycle);
        this.registerList = registerList;
        this.measurableItem = measurableItem;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            // DATA
            case 0:
                ExperimentViewDataMeasuresFragment viewRegistersFragment = new ExperimentViewDataMeasuresFragment();
                return viewRegistersFragment;
            // GRAPH
            case 1:
                if(measurableItem instanceof MySensor) {
                    if (((MySensor)measurableItem).getSensorType() != SensorConfigConstants.TYPE_GPS) {
                        return new ExperimentViewSensorGraphFragment();
                    } else {
//                        MeasuresMapFragment measuresMapFragment = new MeasuresMapFragment();
//                        return measuresMapFragment;
                    }
                }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
