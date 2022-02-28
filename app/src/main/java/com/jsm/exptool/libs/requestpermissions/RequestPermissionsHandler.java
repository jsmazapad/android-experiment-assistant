package com.jsm.exptool.libs.requestpermissions;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
}
