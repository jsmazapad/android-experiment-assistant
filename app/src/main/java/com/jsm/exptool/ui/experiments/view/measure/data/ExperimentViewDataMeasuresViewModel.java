package com.jsm.exptool.ui.experiments.view.measure.data;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.entities.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.List;

public class ExperimentViewDataMeasuresViewModel extends BaseRecyclerViewModel<ExperimentRegister, ExperimentRegister> {

    RepeatableElementConfig measurableItem;
    private String headerElementValueField;
    private boolean shortHeaderElementValue;

    public ExperimentViewDataMeasuresViewModel(Application app, RepeatableElementConfig measurableItem, List<ExperimentRegister> measures) {
        super(app, measurableItem,  measures);
        shortHeaderElementValue = false;
        if(measurableItem instanceof CameraConfig){
            headerElementValueField = app.getString(R.string.experiment_view_data_embedding_header);
            shortHeaderElementValue = true;
        }else if(measurableItem instanceof AudioConfig){
            headerElementValueField = "";
        }else if(measurableItem instanceof SensorConfig){
            headerElementValueField = app.getString(R.string.experiment_view_data_sensor_value_header);
        }else if (measurableItem instanceof LocationConfig){
            headerElementValueField = app.getString(R.string.experiment_view_data_location_position_header);
        }else{
            //Comentario
            headerElementValueField = app.getString(R.string.experiment_view_data_comment_value_header);
        }
    }

    public String getHeaderElementValueField() {
        return headerElementValueField;
    }

    public boolean isShortHeaderElementValue() {
        return shortHeaderElementValue;
    }

    @Override
    public List<ExperimentRegister> transformResponse(ListResponse<ExperimentRegister> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {
        this.measurableItem = (RepeatableElementConfig) args[0];
        this.apiResponseRepositoryHolder.setValue(new ListResponse<>((List<ExperimentRegister>) args [1]));

    }

    @Override
    public void callRepositoryForData() {

    }
}
