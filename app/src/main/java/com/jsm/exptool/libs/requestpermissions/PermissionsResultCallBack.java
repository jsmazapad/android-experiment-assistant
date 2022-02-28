package com.jsm.exptool.libs.requestpermissions;

import java.util.List;

public interface PermissionsResultCallBack {

    void onPermissionsAccepted();
    void onPermissionsError(List<String> rejectedPermissions);
}
