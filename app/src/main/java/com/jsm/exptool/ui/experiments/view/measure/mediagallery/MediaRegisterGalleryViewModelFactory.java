package com.jsm.exptool.ui.experiments.view.measure.mediagallery;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.experimentconfig.MultimediaConfig;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery.AudioRegisterGalleryViewModel;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.imagegallery.ImageRegisterGalleryViewModel;

import java.util.List;


public class MediaRegisterGalleryViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private MultimediaConfig mediaElement;
    private List<ExperimentRegister> mediaRegisters;


    public MediaRegisterGalleryViewModelFactory(Application app, List<ExperimentRegister> mediaRegisters) {
        this.app = app;
        this.mediaRegisters = mediaRegisters;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == MediaRegisterGalleryViewModel.class) {
            return (T) new MediaRegisterGalleryViewModel(app, mediaRegisters);
        }else  if (modelClass == ImageRegisterGalleryViewModel.class){
            return (T) new ImageRegisterGalleryViewModel(app, mediaRegisters);
        }else  if (modelClass == AudioRegisterGalleryViewModel.class){
            return (T) new AudioRegisterGalleryViewModel(app, mediaRegisters);
        }else{
            return null;
        }
    }
}
