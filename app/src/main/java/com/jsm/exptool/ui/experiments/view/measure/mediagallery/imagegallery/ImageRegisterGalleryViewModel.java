package com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery;

import android.app.Application;
import android.widget.ImageView;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.providers.ImagesProvider;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModel;

import java.util.List;

public class ImageRegisterGalleryViewModel extends MediaRegisterGalleryViewModel {




    public ImageRegisterGalleryViewModel(Application app, List<ExperimentRegister> imageRegisters) {
        super(app, imageRegisters);

    }



    @Override
    public void callRepositoryForData() {

    }


    public void loadFullImage(String path, ImageView imageIV){
        if(path != null) {
            ImagesProvider.loadImageForListOrDetail(path, imageIV, false);
        }
    }
}