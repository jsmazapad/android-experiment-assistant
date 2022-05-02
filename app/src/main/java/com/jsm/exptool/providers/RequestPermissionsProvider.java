package com.jsm.exptool.providers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.jsm.exptool.libs.requestpermissions.PermissionsResultCallBack;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsHandler;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

public class RequestPermissionsProvider {

    private static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String[] RECORDING_AUDIO_PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO
    };

    private static final String[] LOCATION_FINE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final String[] LOCATION_COARSE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static ActivityResultLauncher<String[]> registerForCameraPermissions(Fragment fragment, PermissionsResultCallBack callback){
        return RequestPermissionsHandler.registerToRequestPermissions(fragment, CAMERA_PERMISSIONS, callback);
    }

    public static ActivityResultLauncher<String[]> registerForAudioPermissions(Fragment fragment, PermissionsResultCallBack callback){
        return RequestPermissionsHandler.registerToRequestPermissions(fragment, RECORDING_AUDIO_PERMISSIONS, callback);
    }

    public static ActivityResultLauncher<String[]> registerForLocationFinePermissions(Fragment fragment, PermissionsResultCallBack callback){
        return RequestPermissionsHandler.registerToRequestPermissions(fragment, LOCATION_FINE_PERMISSIONS, callback);
    }

    public static ActivityResultLauncher<String[]> registerForLocationCoarsePermissions(Fragment fragment, PermissionsResultCallBack callback){
        return RequestPermissionsHandler.registerToRequestPermissions(fragment, LOCATION_COARSE_PERMISSIONS, callback);
    }

    public static void requestPermissionsForCamera(ActivityResultLauncher<String[]> mPermissionResultHandler) {
        RequestPermissionsHandler.requestPermissions(mPermissionResultHandler, CAMERA_PERMISSIONS);
    }

    public static boolean handleCheckPermissionsForCamera(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, CAMERA_PERMISSIONS);
    }


    public static void requestPermissionsForAudioRecording(ActivityResultLauncher<String[]> mPermissionResultHandler) {
        RequestPermissionsHandler.requestPermissions(mPermissionResultHandler, RECORDING_AUDIO_PERMISSIONS);
    }

    public static boolean handleCheckPermissionsForAudio(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, RECORDING_AUDIO_PERMISSIONS);

    }

    public static void requestPermissionsForLocationFine(ActivityResultLauncher<String[]> mPermissionResultHandler) {
        RequestPermissionsHandler.requestPermissions(mPermissionResultHandler, LOCATION_FINE_PERMISSIONS);
    }

    public static boolean handleCheckPermissionsForLocationFine(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, LOCATION_FINE_PERMISSIONS);

    }

    public static void requestPermissionsForLocationCoarse(ActivityResultLauncher<String[]> mPermissionResultHandler) {
        RequestPermissionsHandler.requestPermissions(mPermissionResultHandler, LOCATION_COARSE_PERMISSIONS);
    }

    public static boolean handleCheckPermissionsForLocationCoarse(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, LOCATION_COARSE_PERMISSIONS);

    }



}
