package com.jsm.exptool.libs.camera;


import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.view.PreviewView;

import com.jsm.exptool.R;

import java.io.File;

public class CameraProvider {


    CameraXHandler cameraHandler;

    private static CameraProvider INSTANCE = null;

    // other instance variables can be here

    private CameraProvider() {
        cameraHandler = new CameraXHandler();
    };

    public static synchronized CameraProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CameraProvider();
        }
        return(INSTANCE);
    }

    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback, FlashModes flashMode, CameraPositions cameraPosition){
        cameraHandler.initCamera(context, previewView, imageReceivedCallback, flashMode.flashMode, cameraPosition.cameraPositionMode);
    }

    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback){
        cameraHandler.initCamera(context, previewView, imageReceivedCallback);
    }

    //No necesario porque se agrega la cámara al ciclo de vida y se desconecta sola
//    public void closeCamera(){
//    }

    public void setFlashMode(Context context, FlashModes mode){
        cameraHandler.changeFlash(context, mode.flashMode);
    }

    public FlashModes getFlashMode(){
        return CameraProvider.FlashModes.chooseByFlashMode(cameraHandler.getFlashMode());
    }

    public CameraPositions switchCamera(Context context){
        return CameraPositions.chooseByLensFacing(cameraHandler.switchLensFacing(context));
    }

    public CameraPositions getCameraPosition(){
        return CameraProvider.CameraPositions.chooseByLensFacing(cameraHandler.getLensFacing());
    }

    public void takePicture(File f){
        cameraHandler.takePicture(f);
    }

    public void takePicture(File f, ImageReceivedCallback imageReceivedCallback){
        cameraHandler.takePicture(f, imageReceivedCallback);
    }



    public int getFlashImageResource(FlashModes mode){
        switch (mode){
            case ON:
                return R.drawable.ic_flash_on;
            case OFF:
                return R.drawable.ic_flash_off;
            case AUTO:
                return R.drawable.ic_flash_auto;
            default:
                return R.drawable.ic_midline_24;
        }
    }

    public int getCameraPositionImageResource(CameraPositions mode){
        switch (mode){
            case FRONT:
                return R.drawable.ic_baseline_camera_front_24;
            case REAR:
                return R.drawable.ic_baseline_camera_rear_24;
            default:
                return R.drawable.ic_flip_camera;
        }
    }

    public enum FlashModes {
        OFF(ImageCapture.FLASH_MODE_OFF),
        AUTO(ImageCapture.FLASH_MODE_AUTO),
        ON(ImageCapture.FLASH_MODE_ON);

        public final int flashMode;


        FlashModes(int flashMode) {
            this.flashMode = flashMode;
        }

        public static FlashModes chooseByFlashMode(int imageCaptureMode){
            FlashModes returnValue = null;
            for (FlashModes mode: FlashModes.values()) {
                if (mode.flashMode == imageCaptureMode)
                {
                    returnValue = mode;
                }

            }
            return returnValue;
        }
    }

    public enum CameraPositions{
        REAR(CameraSelector.LENS_FACING_BACK),
        FRONT(CameraSelector.LENS_FACING_FRONT);

        public final int cameraPositionMode;


        CameraPositions(int cameraPositionMode) {
            this.cameraPositionMode = cameraPositionMode;
        }

        public static CameraPositions chooseByLensFacing(int cameraPosition){
            CameraPositions returnValue = null;
            for (CameraPositions mode: CameraPositions.values()) {
                if (mode.cameraPositionMode == cameraPosition)
                {
                    returnValue = mode;
                }

            }
            return returnValue;
        }
    }
}
