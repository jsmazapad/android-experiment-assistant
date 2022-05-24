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
public interface ImageRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME)
    List<ImageRegister> getImages();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE _id = :id LIMIT 1")
    ImageRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE experimentId = :experimentId ORDER BY date DESC")
    List<ImageRegister> getImageRegistersByExperimentId(long experimentId);

    /**
     * Obtiene los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 OR (embedding NOT LIKE '' AND embeddingRemoteSynced = 0) ORDER BY date ASC LIMIT :limit")
    List<ImageRegister> getPendingSyncImageRegistersByExperimentId(long experimentId, int limit);

    /**
     * Obtiene los registros con archivos pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND fileRemoteSynced = 0 ORDER BY date ASC LIMIT :limit")
    List<ImageRegister> getPendingFileSyncImageRegistersByExperimentId(long experimentId, int limit);

    /**
     * Obtiene los registros que tienen datos de embedding asociados
     * @param experimentId
     * @return
     */
    @Query("SELECT * FROM "+ ImageRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND (embedding NOT NULL OR embedding NOT LIKE '')ORDER BY date DESC")
    List<ImageRegister> getImageRegistersWithEmbeddingByExperimentId(long experimentId);
    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(ImageRegister register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(ImageRegister register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + ImageRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);

    /**
     * Elimina los registros asociado a un experimentId
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + ImageRegister.TABLE_NAME + " WHERE experimentId = :id")
    int deleteByExperimentId(long id);
}
