package com.jsm.exptool.ui.experiments.view.measure.mediagallery.audiogallery;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.providers.ImagesProvider;
import com.jsm.exptool.ui.experiments.view.measure.mediagallery.MediaRegisterGalleryViewModel;

import java.io.File;
import java.util.List;

public class AudioRegisterGalleryViewModel extends MediaRegisterGalleryViewModel {

    ExoPlayer player;


    public AudioRegisterGalleryViewModel(Application app, List<ExperimentRegister> imageRegisters) {
        super(app, imageRegisters);

    }

    @Override
    public List<MediaRegister> transformResponse(ListResponse<MediaRegister> response) {
        return response.getResultList();
    }



    @Override
    public void callRepositoryForData() {

    }

    public void initPlayer(Context context, StyledPlayerView playerView){
        player = new ExoPlayer.Builder(context).build();
// Attach player to the view.
        playerView.setPlayer(player);
    }


    public void loadAudio(String path, Context context){
        try {
            if (path != null) {
                File file = new File(path);
                // Set the media item to be played.
                player.setMediaItem(MediaItem.fromUri(Uri.fromFile(file)));

                // Prepare the player.
                player.prepare();
                player.play();
            }
        }catch (Exception e){
            e.printStackTrace();
            closeViewer();
            handleError(new BaseException("Error al reproducir archivo", false), context );
        }
    }

    @Override
    public void closeViewer() {
        super.closeViewer();
        player.stop();

    }
}