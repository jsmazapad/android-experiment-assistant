package com.jsm.exptool.core.utils;

import static android.content.Context.ACTIVITY_SERVICE;
import static java.util.jar.Pack200.Packer.ERROR;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MemoryUtils {

    //TODO Hay mejor método con File.getUsableSpace()

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String getMemoryForFilePath(File path) {
        if (externalMemoryAvailable()) {
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        double convertedSize = Long.valueOf(size).doubleValue();

        if (convertedSize >= 1024) {
            suffix = "KB";
            convertedSize /= 1024.0;
            if (convertedSize >= 1024) {
                suffix = "MB";
                convertedSize /= 1024.0;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(String.format("%.2f", convertedSize));

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static long getRamSize(Context context) {
        ActivityManager actManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        return totalMemory;
    }

    public static long getFileSize(final File file) {
        if (file == null || !file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();
        final List<File> dirs = new LinkedList<>();
        dirs.add(file);
        long result = 0;
        while (!dirs.isEmpty()) {
            final File dir = dirs.remove(0);
            if (!dir.exists())
                continue;
            final File[] listFiles = dir.listFiles();
            if (listFiles == null || listFiles.length == 0)
                continue;
            for (final File child : listFiles) {
                result += child.length();
                if (child.isDirectory())
                    dirs.add(child);
            }
        }
        return result;
    }

    /**
     * Calcula el tamaño de un fichero o directorio (incluyendo sus subdirectorios)
     *
     * @param path Fichero o directorio del que se desea obtener el tamaño
     * @return
     */
    public static String getFormattedFileSize(File path) {
        return formatSize(getFileSize(path));
    }

    public static boolean deleteDirectory(File path) {
        try {
            FileUtils.deleteDirectory(path);
            return true;
        } catch (IOException exception) {
            return false;
        }
    }
    //

//    /**
//     * Query the media store for a directory size
//     *
//     * @param context
//     *     the application context
//     * @param file
//     *     the directory on primary storage
//     * @return the size of the directory
//     */
//    public static long getFolderSize(Context context, File file) {
//        File directory = readlink(file); // resolve symlinks to internal storage
//        String path = directory.getAbsolutePath();
//        Cursor cursor = null;
//        long size = 0;
//        try {
//            cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
//                    new String[]{MediaStore.MediaColumns.SIZE},
//                    MediaStore.MediaColumns.DATA + " LIKE ?",
//                    new String[]{path + "/%"},
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    size += cursor.getLong(0);
//                } while (cursor.moveToNext());
//            }
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return size;
//    }
//
//    /**
//     * Canonicalize by following all symlinks. Same as "readlink -f file".
//     *
//     * @param file
//     *     a {@link File}
//     * @return The absolute canonical file
//     */
//    public static File readlink(File file) {
//        File f;
//        try {
//            f = file.getCanonicalFile();
//        } catch (IOException e) {
//            return file;
//        }
//        if (f.getAbsolutePath().equals(file.getAbsolutePath())) {
//            return f;
//        }
//        return readlink(f);
//    }

}
