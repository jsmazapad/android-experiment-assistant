package com.jsm.exptool.providers;

import android.content.Context;

import java.io.File;

public class FilePathsProvider {

    public enum PathTypes{
        IMAGES,
        AUDIO,
        EXPORTED_FILES,
        OTHER
    }

    private static final String IMAGES_DIR = "images";
    private static final String EXPORTED_FILES_DIR = "exported_files";
    private static final String GENERAL_FILES_DIR = "other_files";
    private static final String AUDIO_DIR = "audio";

    public static File getFilePathForExperimentItem(Context context, long experimentId, PathTypes pathType ){

        String dirName = GENERAL_FILES_DIR;
        switch (pathType){
            case AUDIO:
                dirName = AUDIO_DIR;
                break;
            case IMAGES:
                dirName = IMAGES_DIR;
                break;
            case EXPORTED_FILES:
                dirName = EXPORTED_FILES_DIR;
                break;
        }

        File filePath =  context.getExternalFilesDir(experimentId + File.separator + dirName);
        if (!filePath.exists())
            filePath.mkdir();

        return filePath;

    }

    public static File getExperimentFilePath(Context context, long experimentId){
        return context.getExternalFilesDir(String.valueOf(experimentId));
    }


}
