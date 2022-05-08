package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.List;

@Dao
public interface SensorRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ SensorRegister.TABLE_NAME)
    List<SensorRegister> getSensors();

    /**
     * Selecciona todas las entidades de la BD de un tipo de sensor y experimento en concreto
     * @return
     */
    @Query("SELECT * FROM "+ SensorRegister.TABLE_NAME + " WHERE sensorType = :type AND experimentId = :experimentId ORDER BY date DESC")
    List<SensorRegister> getSensorsByTypeAndExperimentId(int type, long experimentId);

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ SensorRegister.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    SensorRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ SensorRegister.TABLE_NAME + " WHERE experimentId" + " = :experimentId")
    List<SensorRegister> getSensorsByExperimentId(long experimentId);

    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(SensorRegister register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(SensorRegister register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + SensorRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);

    /**
     * Elimina los registros asociado a un experimentId
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + SensorRegister.TABLE_NAME + " WHERE experimentId = :id")
    int deleteByExperimentId(long id);
    
}
