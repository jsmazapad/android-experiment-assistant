package com.jsm.exptool.data.database;

import static com.jsm.exptool.config.ConfigConstants.REGISTERS_SYNC_LIMIT;

import android.content.Context;

import androidx.room.Room;

import com.jsm.exptool.entities.CommentSuggestion;
import com.jsm.exptool.entities.QuickCommentsCollection;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.ExperimentListFiltersProvider;

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

    public static List<Experiment> getExperiments(Experiment.ExperimentStatus statusFilter, ExperimentListFiltersProvider.ConditionFilterOptions conditionFilterOptions, boolean conditionValue) {
        return appDatabase.experimentDao().getExperimentsWithSensors(statusFilter, conditionFilterOptions, conditionValue);

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

    public static List<ImageRegister> getImageRegisters() {
        return appDatabase.imageDao().getImages();
    }

    public static List<ImageRegister> getImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersByExperimentId(experimentId);
    }

    public static int countImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersCountByExperimentId(experimentId);
    }


    public static List<ImageRegister> getPendingSyncImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingSyncImageRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingSyncImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingSyncImageRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static List<ImageRegister> getPendingFileSyncImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingFileSyncImageRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingFileSyncImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingFileSyncImageRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static List<ImageRegister> getPendingEmbeddingImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingEmbeddingImageRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingEmbeddingImageRegistersByExperimentId(long experimentId) {
        return appDatabase.imageDao().getPendingEmbeddingImageRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static List<ImageRegister> getImageRegistersWithEmbeddingByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersWithEmbeddingByExperimentId(experimentId);
    }

    public static int countImageRegistersWithEmbeddingByExperimentId(long experimentId) {
        return appDatabase.imageDao().getImageRegistersWithEmbeddingCountByExperimentId(experimentId);
    }


    public static ImageRegister getImageById(long imageId) {
        return appDatabase.imageDao().selectById(imageId);
    }

    public static int updateImageRegister(ImageRegister register) {
        return appDatabase.imageDao().update(register);
    }

    public static int updateImageRegisterFileSyncedByRegisterId(long registerId) {
        return appDatabase.imageDao().updateFileRemoteSyncedByRegisterId(registerId);
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

    public static List<AudioRegister> getAudioRegisters() {
        return appDatabase.audioDao().getAudios();
    }

    public static List<AudioRegister> getAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getAudioRegistersByExperimentId(experimentId);
    }

    public static int countAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getAudioRegistersCountByExperimentId(experimentId);
    }

    public static List<AudioRegister> getPendingSyncAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getPendingSyncAudioRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingSyncAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getPendingSyncAudioRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static List<AudioRegister> getPendingFileSyncAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getPendingFileSyncAudioRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingFileSyncAudioRegistersByExperimentId(long experimentId) {
        return appDatabase.audioDao().getPendingFileSyncAudioRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }


    public static AudioRegister getAudioById(long imageId) {
        return appDatabase.audioDao().selectById(imageId);
    }

    public static int updateAudioRegister(AudioRegister register) {
        return appDatabase.audioDao().update(register);
    }

    public static int updateAudioRegisterFileSyncedByRegisterId(long registerId) {
        return appDatabase.audioDao().updateFileRemoteSyncedByRegisterId(registerId);
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

    public static int countSensorRegistersByExperimentId(long experimentId) {
        return appDatabase.sensorDao().getSensorsCountByExperimentId(experimentId);
    }

    public static List<SensorRegister> getPendingSyncSensorRegistersByExperimentId(long experimentId) {
        return appDatabase.sensorDao().getPendingSyncSensorRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingSyncSensorRegistersByExperimentId(long experimentId) {
        return appDatabase.sensorDao().getPendingSyncSensorRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static SensorRegister getSensorRegisterById(long sensorId) {
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

    public static CommentRegister getCommentById(long registerId){
        return appDatabase.commentDao().selectById(registerId);
    }

    public static List<CommentRegister> getCommentRegistersByExperimentId(long experimentId) {
        return appDatabase.commentDao().getCommentRegistersByExperimentId(experimentId);
    }

    public static int countCommentRegistersByExperimentId(long experimentId) {
        return appDatabase.commentDao().getCommentRegistersCountByExperimentId(experimentId);
    }

    public static List<CommentRegister> getPendingSyncCommentRegistersByExperimentId(long experimentId) {
        return appDatabase.commentDao().getPendingSyncCommentRegistersByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
    }

    public static int countPendingSyncCommentRegistersByExperimentId(long experimentId) {
        return appDatabase.commentDao().getPendingSyncCommentRegistersCountByExperimentId(experimentId, REGISTERS_SYNC_LIMIT);
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
