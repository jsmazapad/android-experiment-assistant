package com.jsm.exptool.ui.experiments.view.measure;

import static com.jsm.exptool.config.ConfigConstants.EXPERIMENT_REGISTERS_ARG;
import static com.jsm.exptool.config.ConfigConstants.SENSOR_ARG;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentViewRegistersFragmentBinding;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.ui.experiments.view.measure.sensor.data.ExperimentViewSensorMeasuresViewModel;
import com.jsm.exptool.ui.experiments.view.measure.sensor.graph.ExperimentViewSensorGraphFragment;
import com.jsm.exptool.ui.experiments.view.measure.sensor.graph.ExperimentViewSensorGraphViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExperimentViewRegistersFragment extends BaseFragment<ExperimentViewRegistersFragmentBinding, ExperimentViewRegistersViewModel> {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager2 mViewPager;
    private final static int NUM_PAGES = 2;
    private MySensor measurableItem;
    private ArrayList<SensorRegister> sensorRegisterList;

    @Override
    protected ExperimentViewRegistersViewModel createViewModel() {
        return new ViewModelProvider(this).get(ExperimentViewRegistersViewModel.class);
    }

    @Override
    protected ExperimentViewRegistersFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_registers_fragment, container, false);

    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), getLifecycle());
        mViewPager = getActivity().findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public class SectionsPagerAdapter extends FragmentStateAdapter {


        public SectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle args = new Bundle();
            args.putParcelable(SENSOR_ARG, measurableItem);
            args.putParcelableArrayList(EXPERIMENT_REGISTERS_ARG, sensorRegisterList);
            switch (position){
                // DATA
                case 0:
                    return new ExperimentViewRegistersFragment();
                // GRAPH
                case 1:
                    if (measurableItem.getSensorType() != SensorConfigConstants.TYPE_GPS){
                        return new ExperimentViewSensorGraphFragment();
                    }else{
//                        MeasuresMapFragment measuresMapFragment = new MeasuresMapFragment();
//                        return measuresMapFragment;
                    }
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}