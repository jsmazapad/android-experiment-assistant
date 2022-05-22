package com.jsm.exptool.libs;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    public static final int BUFFER_SIZE = 1024;

    public void zip(String[] filePaths, String zipFileName) throws Exception{

//            File zipFile = new File(zipFileName);
//            if(!zipFile.exists())
//            {
//                zipFile.createNewFile();
//            }
            //BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER_SIZE];

            addFilesToZip(filePaths, out, data, "");

            out.close();

    }

    //TODO Ver que error da al añadir imágenes
    private void addFilesToZip(String[] filePaths, ZipOutputStream out, byte[] data, String directoryName) throws IOException {
        BufferedInputStream origin;
        for (String filePath : filePaths) {
            Log.v("Compress", "Adding: " + filePath);

            boolean isDirectory = (new File(filePath)).isDirectory();
            String zipEntryName = directoryName + filePath.substring(filePath.lastIndexOf("/") + 1) + (isDirectory ? "/" : "");

            ZipEntry entry = new ZipEntry(zipEntryName);
            out.putNextEntry(entry);
            if (!isDirectory) {
                int count;
                FileInputStream fi = new FileInputStream(filePath);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);

                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }else{
                File [] directoryFiles = (new File(filePath)).listFiles();
                String [] directoryFilesPaths = new String [directoryFiles.length];
                for (int i= 0; i<directoryFiles.length; i++){
                    directoryFilesPaths[i] = directoryFiles[i].getPath();
                }

                addFilesToZip(directoryFilesPaths, out, data, zipEntryName);
            }
        }
    }
}
