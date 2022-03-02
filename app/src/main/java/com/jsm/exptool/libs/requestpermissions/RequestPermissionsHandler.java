package com.jsm.exptool.libs.requestpermissions;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;

public class RequestPermissionsHandler {
    public static void requestPermissions(Fragment fragment, String[] permissions, PermissionsResultCallBack callback) {
        ActivityResultLauncher<String[]> mPermissionResult = fragment.registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    ArrayList<String> permissionsRejected = new ArrayList<>(permissions.length);
                    for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                        if (entry.getValue()) {
                        } else {
                            permissionsRejected.add(entry.getKey());
                        }
                    }

                    if (permissionsRejected.size() == 0) {
                        callback.onPermissionsAccepted();
                    } else {
                        callback.onPermissionsError(permissionsRejected);
                    }


                });
        mPermissionResult.launch(permissions);
    }

    public static boolean handleCheckPermissionsForElement(Context context, RequestPermissionsInterface requestPermissions, String[] permissions) {
        boolean needToRequestPermission = false;
        for (String permission : permissions) {
            needToRequestPermission = (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED);
            if (needToRequestPermission) {
                break;
            }
        }
        if (needToRequestPermission) {
            requestPermissions.requestPermissions();
        }
        return needToRequestPermission;

    }
}
