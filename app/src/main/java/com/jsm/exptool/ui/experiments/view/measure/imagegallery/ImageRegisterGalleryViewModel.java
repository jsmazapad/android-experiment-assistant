package com.jsm.exptool.ui.experiments.view.measure.imagegallery;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

import java.util.ArrayList;
import java.util.List;

public class ImageRegisterGalleryViewModel extends BaseRecyclerViewModel<ImageRegister, ImageRegister> {




    public ImageRegisterGalleryViewModel(Application app, List<ImageRegister> imageRegisters) {
        super(app, imageRegisters);

    }


    @Override
    public List<ImageRegister> transformResponse(ListResponse<ImageRegister> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {
        this.apiResponseRepositoryHolder.setValue(new ListResponse<>((List<ImageRegister>) args [0]));
    }

    @Override
    public void callRepositoryForData() {

    }
}