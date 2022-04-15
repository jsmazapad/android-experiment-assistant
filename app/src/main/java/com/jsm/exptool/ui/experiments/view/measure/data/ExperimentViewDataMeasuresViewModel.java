package com.jsm.exptool.ui.experiments.view.measure.data;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.util.List;

public class ExperimentViewDataMeasuresViewModel extends BaseRecyclerViewModelListener<ExperimentRegister, ExperimentRegister> {

    RepeatableElement measurableItem;

    public ExperimentViewDataMeasuresViewModel(Application app, RepeatableElement measurableItem, List<ExperimentRegister> measures) {
        super(app, measurableItem,  measures);
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
        this.measurableItem = (RepeatableElement) args[0];
        this.apiResponseRepositoryHolder.setValue(new ListResponse<>((List<ExperimentRegister>) args [1]));

    }

    @Override
    public void callRepositoryForData() {

    }
}
