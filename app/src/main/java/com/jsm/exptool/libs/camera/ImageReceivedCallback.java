package com.jsm.exptool.libs.camera;

import java.io.File;

public interface ImageReceivedCallback {

     void onImageReceived(File imageFile);
     void onErrorReceived(Exception error);


}
