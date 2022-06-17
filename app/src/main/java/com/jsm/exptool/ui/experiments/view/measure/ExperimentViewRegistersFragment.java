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
import com.jsm.exptool.databinding.ExperimentViewRegistersFragmentBinding;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;

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
            mSectionsPagerAdapter = new ExperimentViewRegistersSectionPagerAdapter(getChildFragmentManager(), getLifecycle(), new ArrayList<>(response), viewModel.getMeasurableItem());
            mViewPager = binding.viewPagerContainer;
            //Eliminamos swipe para que sólo se pueda mover tocando las pestañas
            mViewPager.setUserInputEnabled(false);
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

        //Si son comentarios sólo muestra un tab
        if(viewModel.getMeasurableItem().getNameStringResource() == R.string.comments) {
            tabLayout.removeTab(tabLayout.getTabAt(1));
        }

    }

    public ExperimentViewRegistersViewModel getViewModel(){
        return this.viewModel;
    }


    @Override
    protected ExperimentViewRegistersViewModel createViewModel() {

        long experimentId = ExperimentViewRegistersFragmentArgs.fromBundle(getArguments()).getExperimentId();
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