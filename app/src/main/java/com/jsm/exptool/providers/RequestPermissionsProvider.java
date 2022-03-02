package com.jsm.exptool.providers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

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

    public static void requestPermissionsForCamera(Fragment fragment, PermissionsResultCallBack callback) {
        RequestPermissionsHandler.requestPermissions(fragment, CAMERA_PERMISSIONS, callback);
    }

    public static boolean handleCheckPermissionsForCamera(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, CAMERA_PERMISSIONS);
    }


    public static void requestPermissionsForAudioRecording(Fragment fragment, PermissionsResultCallBack callback) {
        RequestPermissionsHandler.requestPermissions(fragment, RECORDING_AUDIO_PERMISSIONS, callback);
    }

    public static boolean handleCheckPermissionsForAudio(Context context, RequestPermissionsInterface requestPermissions) {
        return RequestPermissionsHandler.handleCheckPermissionsForElement(context, requestPermissions, RECORDING_AUDIO_PERMISSIONS);

    }



}
