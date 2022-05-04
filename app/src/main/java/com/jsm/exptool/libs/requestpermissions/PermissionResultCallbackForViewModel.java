package com.jsm.exptool.libs.requestpermissions;

import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.libs.requestpermissions.PermissionsResultCallBack;

public abstract class PermissionResultCallbackForViewModel implements PermissionsResultCallBack {

    BaseViewModel viewModel;

    public void setViewModel(BaseViewModel viewModel) {
        this.viewModel = viewModel;
    }

}
