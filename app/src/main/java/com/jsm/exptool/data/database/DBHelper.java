package com.jsm.exptool.data.database;

import android.content.Context;

import androidx.room.Room;

import com.jsm.exptool.data.database.typeconverters.ExperimentStatusConverter;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.CommentRegister;
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

    private DBHelper() {
    }

    public static void initialize(Context context) {
        sharedDBPool = new DBHelper();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    /*
    EXPERIMENTS
     */

    public static long insertExperiment(final Experiment experiment) {
        return appDatabase.experimentDao().insert(experiment);
    }

    public static List<Experiment> getExperiments(Experiment.ExperimentStatus statusFilter) {
        return appDatabase.experimentDao().getExperimentsWithSensors(statusFilter);

//        if(statusFilter == null) {
//            return appDatabase.experimentDao().getExperiments();
//        }else{
//            return appDatabase.experimentDao().getExperimentsFilteredByState(ExperimentStatusConverter.fromEnum(statusFilter));
//        }

    }


    public static Experiment getExperimentById(long experimentId) {
        return appDatabase.experimentDao().selectById(experimentId);
    }

    /**
     * Elimina un experimento y todos los registros de las tablas asociadas (Configuración de sensores, audio, imagen, sensores)
     * @param register
     * @return
     */
    public static int deleteExperimentById(Experiment register) {
        long id = register.getInternalId();
        appDatabase.audioDao().deleteByExperimentId(id);
        appDatabase.commentDao().deleteByExperimentId(id);
        appDatabase.imageDao().deleteByExperimentId(id);
        appDatabase.sensorDao().deleteByExperimentId(id);
        return appDatabase.experimentDao().deleteById(id);
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

    public static List<ImageRegister> getImageRegistersWithEmbeddingByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersWithEmbeddingByExperimentId(experimentId);
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

    /*
    COMMENT REGISTERS
     */
    public static long insertCommentRegister(final CommentRegister commentRegister) {
        return appDatabase.commentDao().insert(commentRegister);
    }

    public static List<CommentRegister> getCommentRegisters() {
        return appDatabase.commentDao().getComments();
    }

    public static List<CommentRegister> getCommentRegistersByExperimentId(long experimentId) {
        return appDatabase.commentDao().getCommentRegistersByExperimentId(experimentId);
    }


    public static CommentRegister getCommentRegistersById(long sensorId) {
        return appDatabase.commentDao().selectById(sensorId);
    }

    public static int updateCommentRegister(CommentRegister register) {
        return appDatabase.commentDao().update(register);
    }

    public static int deleteCommentRegisterById(CommentRegister register) {
        return appDatabase.commentDao().deleteById(register.getInternalId());
    }

    /*
   COMMENT SUGGESTIONS
    */
    public static long insertCommentSuggestion(final CommentSuggestion commentRegister) {
        return appDatabase.commentSuggestionDao().insert(commentRegister);
    }

    public static long insertOrUpdateCommentSuggestion(final CommentSuggestion commentRegister) {
        return appDatabase.commentSuggestionDao().insertOrUpdate(commentRegister);
    }

    public static List<CommentSuggestion> getCommentSuggestions(String stringToSearch) {
        if (stringToSearch == null || "".equals(stringToSearch.trim())) {
            return appDatabase.commentSuggestionDao().getCommentSuggestions();
        } else {
            return appDatabase.commentSuggestionDao().getCommentSuggestionsFilterByString("%" + stringToSearch + "%");
        }

    }

    public static List<CommentSuggestion> checkIfCommentSuggestionExists(String stringToSearch) {
        return appDatabase.commentSuggestionDao().getCommentSuggestionsFilterByString(stringToSearch);
    }

    public static CommentSuggestion selectCommentSuggestionByComment(String stringToSearch) {
        return appDatabase.commentSuggestionDao().selectByComment(stringToSearch);
    }


    public static CommentSuggestion getCommentSuggestionById(long id) {
        return appDatabase.commentSuggestionDao().selectById(id);
    }

    public static int updateCommentSuggestion(CommentSuggestion register) {
        return appDatabase.commentSuggestionDao().update(register);
    }

    public static int deleteCommentSuggestionById(CommentSuggestion register) {
        return appDatabase.commentSuggestionDao().deleteById(register.getInternalId());
    }

    public static long resetSuggestionsCounter() {
        return appDatabase.commentSuggestionDao().resetSuggestionsCounter();
    }

    /*
  QUICK COMMENT COLLECTIONS
   */
    public static long insertQuickCommentsCollection(final QuickCommentsCollection commentRegister) {
        return appDatabase.quickCommentsCollectionDao().insert(commentRegister);
    }

    public static List<QuickCommentsCollection> getQuickCommentsCollections() {

        return appDatabase.quickCommentsCollectionDao().getQuickCommentsCollections();


    }


    public static int updateQuickCommentsCollection(QuickCommentsCollection register) {
        return appDatabase.quickCommentsCollectionDao().update(register);
    }

    public static int deleteQuickCommentsCollectionById(QuickCommentsCollection register) {
        return appDatabase.quickCommentsCollectionDao().deleteById(register.getInternalId());
    }

}
