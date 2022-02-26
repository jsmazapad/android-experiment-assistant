package com.jsm.exptool.libs.camera;

import android.content.Context;
import android.util.Log;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraXHandler {

    /**
     * Use case to take a picture
     */
    private ImageCapture imageCapture;
    /**
     * Callback to pass the image result
     */
    private ImageReceivedCallback imageReceivedCallback;

    /**
    Camera instance
     */
    private Camera camera;
    private PreviewView previewView;

    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private int flashMode = ImageCapture.FLASH_MODE_OFF;

    public int  switchLensFacing(Context context){
        lensFacing = lensFacing == CameraSelector.LENS_FACING_BACK ? CameraSelector.LENS_FACING_FRONT : CameraSelector.LENS_FACING_BACK;
        initCamera(context, previewView, imageReceivedCallback);
        return lensFacing;

    }

    public int getLensFacing(){
        return lensFacing;
    }

    public void changeFlash(Context context, int flashMode){
        this.flashMode = flashMode;
        initCamera(context, previewView, imageReceivedCallback);
    }

    public int getFlashMode(){
        return flashMode;
    }

    public void setFlashMode(int flashMode){
        this.flashMode = flashMode;
    }




    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback, int flashMode, int lensFacing){
        this.flashMode = flashMode;
        this.lensFacing = lensFacing;
        initCamera(context, previewView, imageReceivedCallback);
    }

    public void initCamera(Context context, PreviewView previewView, ImageReceivedCallback imageReceivedCallback){

        this.imageReceivedCallback = imageReceivedCallback;
        this.previewView = previewView;


        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(context);

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
                //Focus on minimize latency instead of maximize quality
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setFlashMode(flashMode)
                        .build();

                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build();

                // Unbind use cases before rebinding
                cameraProvider.unbindAll();

                // Attach use cases to the camera with the same lifecycle owner
                camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner)context),
                        cameraSelector,
                        preview,
                        imageCapture);

                if(previewView != null) {
                    // Connect the preview use case to the previewView
                    preview.setSurfaceProvider(
                            previewView.getSurfaceProvider());
                }
            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void takePicture(File mFile, ImageReceivedCallback imageReceivedCallback) {
        if (imageReceivedCallback != null) {
            this.imageReceivedCallback = imageReceivedCallback;
        }
        this.takePicture(mFile);
    }

    //TODO Desacoplar de init imageReceivedCallback y recibirlo desde aquí
    public void takePicture(File mFile){
        if (imageReceivedCallback != null){
            this.imageReceivedCallback = imageReceivedCallback;
        }
        Executor cameraExecutor =  Executors.newSingleThreadExecutor();
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(mFile).build();
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        // insert your code here.
                        Log.d("ImageSaved",outputFileResults.getSavedUri().getPath());
                        if(imageReceivedCallback != null) {
                            imageReceivedCallback.onImageReceived(new File(outputFileResults.getSavedUri().getPath()));
                        }
                    }
                    @Override
                    public void onError(ImageCaptureException error) {
                        // insert your code here.
                        error.printStackTrace();
                        if(imageReceivedCallback != null) {
                            imageReceivedCallback.onErrorReceived(error);
                        }
                    }
                }
        );



    }

    public boolean hasFlash(){
        return camera.getCameraInfo().hasFlashUnit();

    }
}
