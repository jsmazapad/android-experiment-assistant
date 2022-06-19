package com.jsm.exptool.providers;


import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.view.PreviewView;

import com.jsm.exptool.R;
import com.jsm.exptool.libs.camera.CameraXHandler;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;

import java.io.File;

public class CameraProvider {


    /**
     * Manejador de la cámara
     */
    CameraXHandler cameraHandler;

    private static CameraProvider INSTANCE = null;

    // other instance variables can be here

    private CameraProvider() {
        cameraHandler = new CameraXHandler();
    }


    public static synchronized CameraProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CameraProvider();
        }
        return (INSTANCE);
    }

    /**
     * Inicializa la cámara
     *
     * @param context
     * @param previewView
     * @param imageReceivedCallback
     * @param flashMode
     * @param cameraPosition
     */
    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback, FlashModes flashMode, CameraPositions cameraPosition) {
        cameraHandler.initCamera(context, previewView, imageReceivedCallback, flashMode.flashMode, cameraPosition.cameraPositionMode);
    }

    /**
     * Inicializa la cámara
     *
     * @param context
     * @param previewView
     * @param imageReceivedCallback
     */
    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback) {
        cameraHandler.initCamera(context, previewView, imageReceivedCallback);
    }

    //No necesario porque se agrega la cámara al ciclo de vida y se desconecta sola
//    public void closeCamera(){
//    }

    /**
     * Setea el tipo de flash de la cámara
     *
     * @param context
     * @param mode
     */
    public void setFlashMode(Context context, FlashModes mode) {
        cameraHandler.changeFlash(context, mode.flashMode);
    }

    /**
     * Obtiene el tipo de flash de la cámara
     *
     * @return
     */
    public FlashModes getFlashMode() {
        return CameraProvider.FlashModes.chooseByFlashMode(cameraHandler.getFlashMode());
    }

    /**
     * Rota entre la cámara delantera y la trasera
     *
     * @param context
     * @return La posición a la que ha rotado la cámara
     */
    public CameraPositions switchCamera(Context context) {
        return CameraPositions.chooseByLensFacing(cameraHandler.switchLensFacing(context));
    }

    /**
     * Obtiene la posición de la cámara
     *
     * @return
     */
    public CameraPositions getCameraPosition() {
        return CameraProvider.CameraPositions.chooseByLensFacing(cameraHandler.getLensFacing());
    }

    /**
     * Realiza una foto con el callback proporcionado al iniciar la cámara
     *
     * @param f
     */
    public void takePicture(File f) {
        cameraHandler.takePicture(f);
    }

    /**
     * Realiza una foto sustituyendo el callback de la cámara
     *
     * @param f
     * @param imageReceivedCallback
     */
    public void takePicture(File f, ImageReceivedCallback imageReceivedCallback) {
        cameraHandler.takePicture(f, imageReceivedCallback);
    }


    /**
     * Obtiene una imagen asociada al modo de flash proporcionado
     *
     * @param mode
     * @return
     */

    public int getFlashImageResource(FlashModes mode) {
        switch (mode) {
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

    /**
     * Obtiene una imagen asociada a la posición de la cámara proporcionada
     *
     * @param mode
     * @return
     */
    public int getCameraPositionImageResource(CameraPositions mode) {
        switch (mode) {
            case FRONT:
                return R.drawable.ic_baseline_camera_front_24;
            case REAR:
                return R.drawable.ic_baseline_camera_rear_24;
            default:
                return R.drawable.ic_flip_camera;
        }
    }

    /**
     * Tipos de Flash
     */
    public enum FlashModes {
        OFF(ImageCapture.FLASH_MODE_OFF),
        AUTO(ImageCapture.FLASH_MODE_AUTO),
        ON(ImageCapture.FLASH_MODE_ON);

        public final int flashMode;


        FlashModes(int flashMode) {
            this.flashMode = flashMode;
        }

        public static FlashModes chooseByFlashMode(int imageCaptureMode) {
            FlashModes returnValue = null;
            for (FlashModes mode : FlashModes.values()) {
                if (mode.flashMode == imageCaptureMode) {
                    returnValue = mode;
                    break;
                }

            }
            return returnValue;
        }
    }

    /**
     * Posiciones de la cámara
     */
    public enum CameraPositions {
        REAR(CameraSelector.LENS_FACING_BACK),
        FRONT(CameraSelector.LENS_FACING_FRONT);

        public final int cameraPositionMode;


        CameraPositions(int cameraPositionMode) {
            this.cameraPositionMode = cameraPositionMode;
        }

        public static CameraPositions chooseByLensFacing(int cameraPosition) {
            CameraPositions returnValue = null;
            for (CameraPositions mode : CameraPositions.values()) {
                if (mode.cameraPositionMode == cameraPosition) {
                    returnValue = mode;
                }

            }
            return returnValue;
        }
    }
}
