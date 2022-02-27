package com.jsm.exptool.data.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.ImageRegister;

import java.util.List;


/**
 * Helper para manipular la BD
 * nos abstrae de la tecnología empleada para la capa de datos
 */
public class DBHelper {

    private static DBHelper sharedDBPool;
    private static String DB_NAME = "db_experiments";
    private static AppDatabase appDatabase;
    private DBHelper() {}

    public static void initialize(Context context) {
        sharedDBPool= new DBHelper();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    /*
    EXPERIMENTS
     */

    public static long insertExperiment(final Experiment experiment) {
               return appDatabase.experimentDao().insert(experiment);
    }

    public static List<Experiment> getExperiments() {
        return appDatabase.experimentDao().getExperiments();
    }


    public static Experiment getExperimentById(long experimentId) {
        return appDatabase.experimentDao().selectById(experimentId);
    }

    public static int deleteImagesById(Experiment register) {
        return appDatabase.experimentDao().deleteById(register.getInternalId());
    }

    public static int updateExperiment(Experiment register) {
        return appDatabase.experimentDao().update(register);
    }

    /*
    IMAGE REGISTERS
     */


    public static long insertImageRegister(final ImageRegister imageRegister, MutableLiveData<Boolean> ended) {
//            //TODO Migrar a Executors + handlers
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... voids) {
                  return appDatabase.imageDao().insert(imageRegister);
//                    return null;
//                }
//
//                @Override
//                protected void onPostExecute(Void aVoid) {
//                    super.onPostExecute(aVoid);
//                    ended.setValue(true);
//                }
//            }.execute();

    }

    public static List<ImageRegister> getImages() {
        return appDatabase.imageDao().getImages();
    }

    public static List<ImageRegister> getImagesFromExperiment(Experiment experiment) {
        return appDatabase.imageDao().getImagesFromExperiment(experiment.getId());
    }


    public static ImageRegister getImageById(long imageId) {
        return appDatabase.imageDao().selectById(imageId);
    }

    public static int updateImage(ImageRegister register) {
        return appDatabase.imageDao().update(register);
    }

    public static int deleteImagesById(ImageRegister register) {
        return appDatabase.imageDao().deleteById(register.getInternalId());
    }



}
