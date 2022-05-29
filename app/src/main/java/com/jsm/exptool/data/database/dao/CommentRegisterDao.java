package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.List;

@Dao
public interface CommentRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME)
    List<CommentRegister> getComments();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME + " WHERE _id = :id LIMIT 1")
    CommentRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME + " WHERE experimentId = :experimentId ORDER BY date DESC")
    List<CommentRegister> getCommentRegistersByExperimentId(long experimentId);

    /**
     * Obtiene la cuenta de todas las entidades de la BD para un experimento
     * @return
     */
    @Query("SELECT COUNT(*) FROM "+ CommentRegister.TABLE_NAME + " WHERE experimentId = :experimentId ORDER BY date DESC")
    int getCommentRegistersCountByExperimentId(long experimentId);

    /**
     * Obtiene los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 ORDER BY date ASC LIMIT :limit")
    List<CommentRegister> getPendingSyncCommentRegistersByExperimentId(long experimentId, int limit);

    /**
     * Obtiene la cuenta de los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT COUNT(*) FROM "+ CommentRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 ORDER BY date ASC LIMIT :limit")
    int getPendingSyncCommentRegistersCountByExperimentId(long experimentId, int limit);

    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(CommentRegister register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(CommentRegister register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + CommentRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);

    /**
     * Elimina los registros asociado a un experimentId
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + CommentRegister.TABLE_NAME + " WHERE experimentId = :id")
    int deleteByExperimentId(long id);
}
