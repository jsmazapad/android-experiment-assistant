package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.entities.register.SensorRegister;

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
     * Obtiene los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT * FROM "+ SensorRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 ORDER BY date ASC")
    List<SensorRegister> getPendingSyncSensorRegistersByExperimentId(long experimentId);

    /**
     * Obtiene los registros pendientes de sincronización con servidor remoto
     * @param experimentId
     * @param limit
     * @return
     */
    @Query("SELECT COUNT(*) FROM "+ SensorRegister.TABLE_NAME + " WHERE experimentId = :experimentId AND dataRemoteSynced = 0 ORDER BY date ASC")
    int getPendingSyncSensorRegistersCountByExperimentId(long experimentId);

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
     * Obtiene la cuenta de todas las entidades de la BD para un experimento
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT COUNT(*) FROM "+ SensorRegister.TABLE_NAME + " WHERE experimentId" + " = :experimentId")
    int getSensorsCountByExperimentId(long experimentId);

    @Query("SELECT COUNT(*) FROM "+ SensorRegister.TABLE_NAME + " WHERE experimentId = :experimentId and sensorType <> "+ SensorConfigConstants.TYPE_GPS)
    int getSensorsCountWithoutLocationByExperimentId(long experimentId);

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
