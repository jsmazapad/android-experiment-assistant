package com.jsm.exptool.providers;

import android.Manifest;
import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.jsm.exptool.R;
import com.jsm.exptool.libs.requestpermissions.PermissionsResultCallBack;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsHandler;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

import java.util.ArrayList;
import java.util.List;

public class RequestPermissionsProvider {

    public enum PermissionTypes{
        CAMERA,
        AUDIO,
        LOCATION
    }

   private List<PermissionTypes> permissionsToCheckList = new ArrayList<>();



    public void addPermission(PermissionTypes type){
        permissionsToCheckList.add(type);
    }

    public void removePermission(PermissionTypes type){
        if (permissionsToCheckList.contains(type)){
            permissionsToCheckList.remove(type);
        }
    }

    public List<PermissionTypes> getPermissionsToCheckList() {
        return permissionsToCheckList;
    }

    public static String getPermissionErrorString(Context context, List<String> androidPermissionStrings){
        StringBuilder permissionErrorString = new StringBuilder();
        for (String androidPermissionString:androidPermissionStrings
             ) {
            switch (androidPermissionString){
                case Manifest.permission.CAMERA:
                    permissionErrorString.append(context.getString(R.string.camera_permission_rejected));
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    permissionErrorString.append(context.getString(R.string.audio_permission_rejected));
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    permissionErrorString.append(context.getString(R.string.files_permission_rejected));
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    permissionErrorString.append(context.getString(R.string.location_permission_error));
                    break;
            }

        }

        if (!"".equals(permissionErrorString.toString())){
            permissionErrorString.append(context.getString(R.string.permission_rejected_fix_info));
        }
        return permissionErrorString.toString();
    }

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
