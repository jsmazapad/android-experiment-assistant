package com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery;

import android.app.Application;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.providers.ImagesProvider;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModel;

import java.io.File;
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


    public void loadAudio(String path, ExoPlayer player){
        if(path != null) {
            File file = new File(path);
// Set the media item to be played.
            player.setMediaItem(MediaItem.fromUri(Uri.fromFile(file)));

         // Prepare the player.
            player.prepare();
            player.play();
        }
    }
}