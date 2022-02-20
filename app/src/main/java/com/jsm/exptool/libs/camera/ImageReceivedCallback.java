package com.jsm.exptool.libs.camera;

import java.io.File;

public interface ImageReceivedCallback {

    public void onImageReceived(File imageFile);
    public void onErrorReceived(Exception error);


}
