package com.jsm.exptool.ui.experiments.view.measure.mediagallery;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.providers.ImagesProvider;

import java.util.List;

public class MediaRegisterGalleryViewModel extends BaseRecyclerViewModel<ExperimentRegister, ExperimentRegister> {


    private MutableLiveData<Boolean> viewerEnabled = new MutableLiveData<>(false);
    private MutableLiveData<String> fileSelectedPath = new MutableLiveData<>();


    public MediaRegisterGalleryViewModel(Application app, List<ExperimentRegister> imageRegisters) {
        super(app, imageRegisters);

    }

    public MutableLiveData<Boolean> getViewerEnabled() {
        return viewerEnabled;
    }

    public MutableLiveData<String> getFileSelectedPath() {
        return fileSelectedPath;
    }

    @Override
    public List<ExperimentRegister> transformResponse(ListResponse<ExperimentRegister> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        MediaRegister register = (MediaRegister) elements.getValue().get(position);
        fileSelectedPath.setValue(register.getFullPath());

    }

    @Override
    public void setConstructorParameters(Object... args) {
        this.apiResponseRepositoryHolder.setValue(new ListResponse<>((List<ExperimentRegister>) args [0]));
    }

    @Override
    public void callRepositoryForData() {

    }

    public void closeViewer(){
        viewerEnabled.setValue(false);
    }

}