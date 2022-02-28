package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.AudioRegister;

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
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    AudioRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ AudioRegister.TABLE_NAME + " WHERE experimentId" + " = :experimentId LIMIT 1")
    List<AudioRegister> getAudiosFromExperiment(long experimentId);

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
}
