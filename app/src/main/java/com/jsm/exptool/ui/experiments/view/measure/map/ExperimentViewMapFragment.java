package com.jsm.exptool.ui.experiments.view.measure.map;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentViewMapFragmentBinding;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.ui.experiments.view.measure.ExperimentViewRegistersFragment;


import java.util.ArrayList;
import java.util.List;

public class ExperimentViewMapFragment extends BaseFragment<ExperimentViewMapFragmentBinding, ExperimentViewMapViewModel> {

    private SupportMapFragment mapFragment;
    private PolylineOptions polylineOptions;

    @Override
    protected ExperimentViewMapViewModel createViewModel() {
        return new ViewModelProvider(this).get(ExperimentViewMapViewModel.class);
    }

    @Override
    protected ExperimentViewMapFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_map_fragment, container, false);
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        RepeatableElementConfig measurableItem = ((ExperimentViewRegistersFragment) getParentFragment()).getViewModel().getMeasurableItem();
        if(!(measurableItem instanceof LocationConfig))
        {
            return;
        }

        List<ExperimentRegister> registers = ((ExperimentViewRegistersFragment) getParentFragment()).getViewModel().getElements().getValue();
        viewModel.setRegisters(registers);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        polylineOptions = new PolylineOptions();
        setDataValues();
    }

    /**
     * ESTABLECER VALORES DE MEDIDAS
     */
    private void setDataValues(){

        mapFragment.getMapAsync(mMap -> {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            ArrayList<LatLng> coordinateList = new ArrayList<LatLng>();


            //Polyline polyline1 = mMap.addPolyline(new PolylineOptions());
            for (ExperimentRegister register: viewModel.getRegisters()){
                SensorRegister position = (SensorRegister) register;
                if (position.getValue1() != 0.0f && position.getValue2() != 0.0f){
                    LatLng latLng = new LatLng(position.getValue1() , position.getValue2());
                    //TODO Agregar fechas sobre marcadores en misma ubicación
                    MarkerOptions marker = new MarkerOptions().position(latLng).title(DateProvider.dateToDisplayStringWithTime(position.getDate()));
                    mMap.addMarker(marker);
                    coordinateList.add(latLng);


                }
            }

            if (coordinateList.isEmpty()){
                //TODO Tratamiento error o no hace nada
                //Toast.makeText(getContext(), R.string.error , Toast.LENGTH_SHORT).show();
            }else{
                mMap.addMarker(new MarkerOptions().position(coordinateList.get(0)).title(getString(R.string.map_path_init)));
                mMap.addMarker(new MarkerOptions().position(coordinateList.get(coordinateList.size()-1)).title(getString(R.string.map_path_end)));

                polylineOptions.addAll(coordinateList);
                polylineOptions.width(10).color(Color.RED);

                /*
                 * https://developers.google.com/maps/documentation/android-sdk/shapes?hl=es-419#customizing_appearances
                 */
                Polyline polyline = mMap.addPolyline(polylineOptions);
                polyline.setStartCap(new RoundCap());
                polyline.setEndCap(new SquareCap());
                polyline.setJointType(JointType.ROUND);
                //TODO Añadir bounds
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinateList.get(0), 16));
            }
        });
    }
}
