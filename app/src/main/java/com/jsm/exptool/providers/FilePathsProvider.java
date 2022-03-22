package com.jsm.exptool.providers;

import android.content.Context;

import java.io.File;

public class FilePathsProvider {

    public static File getFilesFilePath(Context context){
        return context.getExternalFilesDir(null);
    }

    public static File getImagesFilePath(Context context){
        return context.getExternalFilesDir(null);
    }

    public static File getAudiosFilePath(Context context){
        return context.getExternalFilesDir(null);
    }

}
