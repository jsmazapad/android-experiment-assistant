package com.jsm.exptool.data.database;

import android.content.Context;

import androidx.room.Room;

import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

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


    public static long insertImageRegister(final ImageRegister imageRegister) {
                  return appDatabase.imageDao().insert(imageRegister);
    }

    public static List<ImageRegister> getImages() {
        return appDatabase.imageDao().getImages();
    }


    public static List<ImageRegister> getImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersByExperimentId(experimentId);
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


    /*
    AUDIO REGISTERS
     */
    public static long insertAudioRegister(final AudioRegister audioRegister) {
        return appDatabase.audioDao().insert(audioRegister);
    }

    public static List<AudioRegister> getAudios() {
        return appDatabase.audioDao().getAudios();
    }

    public static List<AudioRegister> getAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getAudioRegistersByExperimentId(experimentId);
    }


    public static AudioRegister getAudioById(long imageId) {
        return appDatabase.audioDao().selectById(imageId);
    }

    public static int updateAudio(AudioRegister register) {
        return appDatabase.audioDao().update(register);
    }

    public static int deleteAudiosById(AudioRegister register) {
        return appDatabase.audioDao().deleteById(register.getInternalId());
    }

    /*
    SENSOR REGISTERS
     */
    public static long insertSensorRegister(final SensorRegister audioRegister) {
        return appDatabase.sensorDao().insert(audioRegister);
    }

    public static List<SensorRegister> getSensorRegisters() {
        return appDatabase.sensorDao().getSensors();
    }

    public static List<SensorRegister> getSensorRegistersByExperimentId(long experimentId) {
        return appDatabase.sensorDao().getSensorsByExperimentId(experimentId);
    }


    public static SensorRegister getSensorRegistersById(long sensorId) {
        return appDatabase.sensorDao().selectById(sensorId);
    }

    public static int updateSensorRegister(SensorRegister register) {
        return appDatabase.sensorDao().update(register);
    }

    public static int deleteSensorRegisterById(SensorRegister register) {
        return appDatabase.sensorDao().deleteById(register.getInternalId());
    }

    public static List<SensorRegister> getSensorRegistersByTypeAndExperimentId(int type, long experimentId) {
        return appDatabase.sensorDao().getSensorsByTypeAndExperimentId(type, experimentId);

    }



}
