package com.jsm.exptool.ui.experiments.view.measure;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.data.mock.MockExamples;
import com.jsm.exptool.databinding.ExperimentViewRegistersFragmentBinding;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.util.ArrayList;

public class ExperimentViewRegistersFragment extends BaseFragment<ExperimentViewRegistersFragmentBinding, ExperimentViewRegistersViewModel> {

    private ExperimentViewRegistersSectionPagerAdapter mSectionsPagerAdapter;
    private ViewPager2 mViewPager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  super.onCreateView(inflater, container, savedInstanceState);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        TabLayout tabLayout = binding.tabs;
        viewModel.getApiResponseMediator().observe(getViewLifecycleOwner(), response->{});
        viewModel.getElements().observe(getViewLifecycleOwner(), response->{
            mSectionsPagerAdapter = new ExperimentViewRegistersSectionPagerAdapter(getChildFragmentManager(), getLifecycle(), new ArrayList<ExperimentRegister>(){{addAll(response);}}, viewModel.getMeasurableItem());
            mViewPager = binding.viewPagerContainer;
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position);
                    tabLayout.selectTab( tabLayout.getTabAt(position));
                }
            });
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        });

        tabLayout.getTabAt(1).setText(viewModel.getSecondTabTitle());

    }

    public ExperimentViewRegistersViewModel getViewModel(){
        return this.viewModel;
    }


    @Override
    protected ExperimentViewRegistersViewModel createViewModel() {

        //SensorConfig measurableItem = ExperimentViewRegistersFragmentArgs.fromBundle(getArguments()).getSensor();
        //long experimentId = ExperimentViewRegistersFragmentArgs.fromBundle(getArguments()).getExperimentId();

        Experiment experiment = MockExamples.registerExperimentForSensorVisualizationTest(getContext());
        long experimentId = experiment.getInternalId();
        //SensorConfig measurableItem = experiment.getConfiguration().getSensorConfig().getSensors().get(0);
        //AudioConfig measurableItem = experiment.getConfiguration().getAudioConfig();
        //CameraConfig measurableItem = experiment.getConfiguration().getCameraConfig();

        RepeatableElementConfig measurableItem = ExperimentViewRegistersFragmentArgs.fromBundle(getArguments()).getElement();

        return new ViewModelProvider(this, new ExperimentViewRegistersViewModelFactory(getActivity().getApplication(), experimentId, measurableItem)).get(ExperimentViewRegistersViewModel.class);
    }

    @Override
    protected ExperimentViewRegistersFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_registers_fragment, container, false);

    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();


    }

}