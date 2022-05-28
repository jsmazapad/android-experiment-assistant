package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ImageRegister;

import java.util.List;


/**
 * DAO para realizar operaciones con la BD,
 * a partir de esta interface se autogenera el código necesario
 */
@Dao
public interface AudioRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME)
    List<AudioRegister> getAudios();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE _id = :id LIMIT 1")
    AudioRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE experimentId = :experimentId ORDER BY date DESC")
    List<AudioRegister> getAudioRegistersByExperimentId(long experimentId);

    /**
     * Obtiene los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 ORDER BY date ASC LIMIT :limit")
    List<AudioRegister> getPendingSyncAudioRegistersByExperimentId(long experimentId, int limit);

    /**
     * Obtiene los registros con archivos pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND fileRemoteSynced = 0 ORDER BY date ASC LIMIT :limit")
    List<AudioRegister> getPendingFileSyncAudioRegistersByExperimentId(long experimentId, int limit);


    /**
     * Actualiza el estado de la sincronización del archivo asociado al registro a partir de su id
     * @param id
     * @return
     */
    @Query("UPDATE " + AudioRegister.TABLE_NAME + " SET fileRemoteSynced = 1  WHERE _id = :id")
    int updateFileRemoteSyncedByRegisterId(long id);


    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(AudioRegister register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(AudioRegister register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + AudioRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);

    /**
     * Elimina los registros asociado a un experimentId
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + AudioRegister.TABLE_NAME + " WHERE experimentId = :id")
    int deleteByExperimentId(long id);
}
