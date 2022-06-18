package com.jsm.exptool.ui.experiments.view.measure;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.data.ExperimentViewDataMeasuresFragment;
import com.jsm.exptool.ui.experiments.view.measure.graph.ExperimentViewSensorGraphFragment;
import com.jsm.exptool.ui.experiments.view.measure.map.ExperimentViewMapFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery.AudioRegisterGalleryFragment;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery.ImageRegisterGalleryFragment;

import java.util.ArrayList;

public class ExperimentViewRegistersSectionPagerAdapter extends FragmentStateAdapter {

    //TODO Quitar registerList, no se usa, viene del fragment padre
    private final ArrayList<ExperimentRegister> registerList;
    private final RepeatableElementConfig measurableItem;
    //TODO Automatizar número de páginas para que sea 1 en comentarios
    private final static int NUM_PAGES = 2;

    public ExperimentViewRegistersSectionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<ExperimentRegister> registerList, RepeatableElementConfig measurableItem) {
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
                if (measurableItem instanceof SensorConfig) {
                    return new ExperimentViewSensorGraphFragment();
                } else if (measurableItem instanceof CameraConfig) {
                    return new ImageRegisterGalleryFragment();
                } else if (measurableItem instanceof AudioConfig) {
                    return new AudioRegisterGalleryFragment();
                } else if (measurableItem instanceof LocationConfig) {
                    return new ExperimentViewMapFragment();
                }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        int itemCount = NUM_PAGES;
//        if(measurableItem.getNameStringResource() == R.string.comments){
//            itemCount = 1;
//        }
        return itemCount;
    }
}
