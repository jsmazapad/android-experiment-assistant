package com.jsm.exptool.ui.camera;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.libs.ImageResizer;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.ImageEmbeddingVector;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CameraViewModel extends BaseViewModel implements ImageReceivedCallback {

    private MutableLiveData<ElementResponse<ImageEmbeddingVector>> serverResponse = new MutableLiveData<>();
    private File actualFile;
    Timer timer = new Timer();
    boolean isActive = false;


    public MutableLiveData<ElementResponse<ImageEmbeddingVector>> getServerResponse() {
        return serverResponse;
    }
    public File getActualFile() {
        return actualFile;
    }

    public CameraViewModel(@NonNull Application application) {
        super(application);
    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            cameraPermission.requestPermissions();
        }
        CameraProvider.getInstance().initCamera(context, previewView, this);
    }



    @Override
    public void onImageReceived(File imageFile) {
        File targetFile = new File(getApplication().getExternalFilesDir(null), "resized"+imageFile.getName());
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResizer.resizeImageFile(imageFile, targetFile, 299);
        actualFile = targetFile;
        ImagesRepository.getEmbedding(serverResponse, targetFile, EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0).getRemoteServerName());
    }

    @Override
    public void onErrorReceived(Exception error) {

    }


    public void takePicture(Context context){

       if (!isActive) {
           isActive = true;
           timer.scheduleAtFixedRate(new TimerTask() {
               @Override
               public void run() {
                   Date date = new Date();
                   File mFile = new File(context.getExternalFilesDir(null), date +"pic.jpg");

                   CameraProvider.getInstance().takePicture(mFile);
               }
           }, 0, 1000);
       }else{
           timer.cancel();
           isActive = false;
       }


    }

    public void selectCamera(Context context){
        CameraProvider.getInstance().switchCamera(context);
    }


    public void changeFlash(View view, Context context){

        CameraProvider.getInstance().setFlashMode(context, getNextFlashMode(CameraProvider.getInstance().getFlashMode()));
    }

    private CameraProvider.FlashModes getNextFlashMode(CameraProvider.FlashModes previousMode){
        CameraProvider.FlashModes flashModeToReturn = null;
        CameraProvider.FlashModes[] values = CameraProvider.FlashModes.values();
        for (int i=0; i< values.length; i++) {
            if (previousMode == values[i]){
                if(i == values.length-1){
                    flashModeToReturn = values[0];
                }else{
                    flashModeToReturn = values[i+1];
                }
            }
        }

        return flashModeToReturn;
    }




}