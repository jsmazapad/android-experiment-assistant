package com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery;

import android.app.Application;
import android.widget.ImageView;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.providers.ImagesProvider;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModel;

import java.util.List;

public class AudioRegisterGalleryViewModel extends MediaRegisterGalleryViewModel {




    public AudioRegisterGalleryViewModel(Application app, List<ExperimentRegister> imageRegisters) {
        super(app, imageRegisters);

    }

    @Override
    public List<ExperimentRegister> transformResponse(ListResponse<ExperimentRegister> response) {
        return response.getResultList();
    }



    @Override
    public void callRepositoryForData() {

    }


    public void loadPlayer(String path, ImageView imageIV){
        if(path != null) {
            ImagesProvider.loadImageForListOrDetail(path, imageIV, false);
        }
    }
}