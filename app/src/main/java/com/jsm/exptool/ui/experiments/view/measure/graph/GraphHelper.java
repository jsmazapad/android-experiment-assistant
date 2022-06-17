package com.jsm.exptool.ui.experiments.view.measure.graph;

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
import com.jsm.exptool.libs.graph.GraphMarkerView;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.DateProvider;

import java.util.ArrayList;

public class GraphHelper {

    private LineChart graph;
    private ArrayList <Entry> valuesX = new ArrayList<>() ;
    private ArrayList <Entry> valuesY = new ArrayList<>() ;
    private ArrayList <Entry> valuesZ = new ArrayList<>() ;
    private ArrayList <String> xAxis_labels = new ArrayList<>() ;
    private ArrayList<ExperimentRegister> measures;
    private boolean isUniqueAxis;

    public GraphHelper(LineChart graph,ArrayList<ExperimentRegister> measures) {
        this.graph = graph;
        this.measures = measures;

        graph.getDescription().setEnabled(false);
        // Comprobar si solo existe un eje (valor)
        SensorRegister firstMeasure = (SensorRegister) measures.get(0);
        if (firstMeasure.getValue2String() != null && !"".equals(firstMeasure.getValue2String())
        && firstMeasure.getValue3String() != null && !"".equals(firstMeasure.getValue3String())) {
            isUniqueAxis = false;
        } else {
            isUniqueAxis = true;
        }
        YAxis rightYAxis = graph.getAxisRight();
        rightYAxis.setEnabled(false);
        setDataValues();
        setDataSets();
        setAxis();
    }

    /**
     * Establece los valores de las medidas de la gráfica
     */
    private void setDataValues(){

            for (int i=0; i < measures.size(); i++){
                SensorRegister measure = (SensorRegister) measures.get(i);
                xAxis_labels.add(DateProvider.dateToDisplayStringWithTime(measure.getDate()).replace(" ", "\n"));
                valuesX.add(new Entry(i, Double.valueOf(measure.getValue1()).floatValue()));
                if(!isUniqueAxis){
                    valuesY.add(new Entry(i, Double.valueOf(measure.getValue2()).floatValue()));
                    valuesZ.add(new Entry(i, Double.valueOf(measure.getValue3()).floatValue()));
                }
            }

    }

    /**
     * Establece los datasets de la gráfica
     */
    private void setDataSets(){
        ArrayList<ILineDataSet> dataSets = new ArrayList<>() ;
        LineDataSet setX = new LineDataSet(valuesX, "X");
        setX.setDrawCircles(false);
        setX.setColor(graph.getContext().getResources().getColor(R.color.graphX));
        setX.setLineWidth(2f);
        dataSets.add(setX);

        if(!isUniqueAxis){
            LineDataSet setY = new LineDataSet(valuesY, "Y");
            LineDataSet setZ = new LineDataSet(valuesZ, "Z");

            dataSets.add(setY);
            dataSets.add(setZ);

            setY.setDrawCircles(false);
            setY.setColor(graph.getContext().getResources().getColor(R.color.graphY));
            setY.setLineWidth(2f);
            setZ.setDrawCircles(false);
            setZ.setColor(graph.getContext().getResources().getColor(R.color.graphZ));
            setZ.setLineWidth(2f);
        }

        LineData lineData = new LineData(dataSets);
        graph.setData(lineData);
        lineData.setDrawValues(false);


        IMarker marker = new GraphMarkerView(graph.getContext(), R.layout.graph_pop);
        graph.setMarker(marker);

        // Tamaño de rango del eje X visible
        graph.setVisibleXRangeMaximum(5);
        graph.fitScreen();
        //graph.moveViewToX(3);
    }

    /**
     * Establece los ejes de la gráfica
     */
    private void setAxis(){
        XAxis xAxis = graph.getXAxis();
        xAxis.setSpaceMax(0.1f);
        graph.setExtraRightOffset(25);
        graph.setExtraBottomOffset(40);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45);
        xAxis.setGranularity(1f);
        xAxis.setXOffset(-3f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxis_labels));
        graph.invalidate();
    }

}
