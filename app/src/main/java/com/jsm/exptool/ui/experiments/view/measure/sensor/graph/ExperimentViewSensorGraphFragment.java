package com.jsm.exptool.ui.experiments.view.measure.sensor.graph;

import android.graphics.drawable.Animatable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentViewSensorGraphFragmentBinding;
import com.jsm.exptool.libs.graph.MyMarkerView;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.providers.DateProvider;

import java.util.ArrayList;

public class ExperimentViewSensorGraphFragment extends BaseFragment<ExperimentViewSensorGraphFragmentBinding, ExperimentViewSensorGraphViewModel> {
    private int measurementId;
    private int sensorType;
    private LineChart graph;
    private ArrayList<SensorRegister> measures;
    private boolean isLoaded = false;
    private boolean isUniqueAxis;
    private ImageView imageView;
    private Animatable animatable;
    private LinearLayout loading;
    private ArrayList <Entry> valuesX = new ArrayList<>() ;
    private ArrayList <Entry> valuesY = new ArrayList<>() ;
    private ArrayList <Entry> valuesZ = new ArrayList<>() ;
    private ArrayList <String> xAxis_labels = new ArrayList<>() ;
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
        measurementId = 1;
        sensorType = 1;

        loading = (LinearLayout) binding.getRoot().findViewById(R.id.loading);
        imageView = loading.findViewById(R.id.loading_logo);
        animatable = (Animatable) imageView.getDrawable();

        graph = (LineChart) binding.getRoot().findViewById(R.id.lineChart);
        //graph.setDragEnabled(true);
        //graph.setScaleEnabled(false);
        graph.getDescription().setEnabled(false);

        YAxis rightYAxis = graph.getAxisRight();
        rightYAxis.setEnabled(false);

        //graph.animateX(1000);
        try {
            loadAllMeasures();
            isLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllMeasures(){
        //TODO Refactorizar a VM y cargar medidas
        measures = new ArrayList<>();
        setDataValues();
        setDataSets();
        setAxis();
    }

        /**
         * ESTABLECER VALORES DE MEDIDAS
         */
        private void setDataValues(){
            if(isUniqueAxis){
                for (int i=0; i < measures.size(); i++){
                    SensorRegister measure = measures.get(i);
                    xAxis_labels.add(DateProvider.dateToDisplayStringWithTime(measure.getDate()));
                    valuesX.add(new Entry(i, measure.getValue1()));
                }
            }else{
                for (int i=0; i < measures.size(); i++){
                    SensorRegister measure = measures.get(i);
                    xAxis_labels.add(DateProvider.dateToDisplayStringWithTime(measure.getDate()));
                    valuesX.add(new Entry(i, measure.getValue1()));
                    valuesY.add(new Entry(i, measure.getValue2()));
                    valuesZ.add(new Entry(i, measure.getValue3()));
                }
            }

        }

    /**
     * ESTABLECER DATASETS DE MEDIDAS
     */
    private void setDataSets(){
        ArrayList <ILineDataSet> dataSets = new ArrayList<>() ;
        LineDataSet setX = new LineDataSet(valuesX, "X");
        setX.setDrawCircles(false);
        setX.setColor(getResources().getColor(R.color.material_dynamic_primary40));
        setX.setLineWidth(2f);
        dataSets.add(setX);

        if(!isUniqueAxis){
            LineDataSet setY = new LineDataSet(valuesY, "Y");
            LineDataSet setZ = new LineDataSet(valuesZ, "Z");

            dataSets.add(setY);
            dataSets.add(setZ);

            setY.setDrawCircles(false);
            setY.setColor(getResources().getColor(R.color.material_dynamic_secondary60));
            setY.setLineWidth(2f);
            setZ.setDrawCircles(false);
            setZ.setColor(getResources().getColor(R.color.material_dynamic_tertiary40));
            setZ.setLineWidth(2f);
        }

        LineData lineData = new LineData(dataSets);
        graph.setData(lineData);
        lineData.setDrawValues(false);

        IMarker marker = new MyMarkerView(getContext(), R.layout.graph_pop);
        graph.setMarker(marker);

        // Tamaño de rango del eje X visible
        graph.setVisibleXRangeMaximum(5);
        graph.fitScreen();
        //graph.moveViewToX(3);
    }

    /**
     * ESTABLECER EJES GRAFICA
     */
    private void setAxis(){
        XAxis xAxis = graph.getXAxis();
        xAxis.setSpaceMax(0.1f);
        graph.setExtraRightOffset(25);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(1f);
        xAxis.setXOffset(-3f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxis_labels));
        graph.invalidate();
    }



}
