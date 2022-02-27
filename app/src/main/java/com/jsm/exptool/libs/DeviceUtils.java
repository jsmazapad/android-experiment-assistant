package com.jsm.exptool.libs;

import android.os.Build;

public class DeviceUtils {

    /**
     * Obtiene el modelo del dispositivo
     * @return
     */
    public static String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.contains(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    /**
     * Obtiene la versión de la SDK del sistema operativo del dispositivo
     * @return
     */
    public static int getDeviceSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }
}
