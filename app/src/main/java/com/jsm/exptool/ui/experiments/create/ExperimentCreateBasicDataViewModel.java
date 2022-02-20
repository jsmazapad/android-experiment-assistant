package com.jsm.exptool.ui.experiments.create;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExperimentCreateBasicDataViewModel extends BaseRecyclerViewModel<MySensor, MySensor> implements MultiSpinner.MultiSpinnerListener, DeleteActionListener<MySensor> {

    private String title = "";
    private String description = "";
    private boolean cameraEnabled = false;
    private boolean audioEnabled = false;
    private List<MySensor> sensors;


    public ExperimentCreateBasicDataViewModel(Application app){
        super(app);
        sensors = SensorsRepository.getSensors();
    }

    @Override
    public List<MySensor> transformResponse(ListResponse<MySensor> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {

    }
    // TODO: Implement the ViewModel


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCameraEnabled() {
        return cameraEnabled;
    }

    public void setCameraEnabled(boolean cameraEnabled) {
        this.cameraEnabled = cameraEnabled;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public void configureSpinner(MultiSpinner spinner){
        List<String> list = new ArrayList<>();
        for (MySensor sensor : sensors) {
            String string = getApplication().getString(sensor.getRName());
            list.add(string);
        }
        spinner.setItems(list, "","Seleccione sensores", this);

    }

    public void selectSensorsButtonClicked(MultiSpinner spinner){
        configureSpinner(spinner);
        spinner.performClick();
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

        List <MySensor> selectedSensors = new ArrayList();
        for(int i=0; i<selected.length; i++){
            if (selected[i]){
                selectedSensors.add(sensors.get(i));
            }
        }
        elements.setValue(selectedSensors);
    }

    @Override
    public void delete(MySensor element) {
        if(element != null) {
            List<MySensor> elementsValue= elements.getValue();
            elementsValue.remove(element);
            elements.setValue(elementsValue);
        }
    }

    public void validateStep(){
        boolean validTitle = this.title != null && !this.title.isEmpty();
        boolean validAtLeastOneOption = audioEnabled || cameraEnabled || (elements.getValue() != null && elements.getValue().size() > 0);
        if (validTitle && validAtLeastOneOption){

        }else{
            if(!validTitle){

            }
            if(!validAtLeastOneOption){
                
            }
        }
    }
}