package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.jsm.exptool.data.database.relations.ExperimentWithSensors;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO para realizar operaciones con la BD,
 * a partir de esta interface se autogenera el código necesario
 */
@Dao
public abstract class ExperimentDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */

    public List<Experiment> getExperiments(){
        List<ExperimentWithSensors> experimentsWithSensors = loadExperimentsWithSensors();
        List<Experiment> experiments = new ArrayList<>(experimentsWithSensors.size());
        for(ExperimentWithSensors experimentWithSensors: experimentsWithSensors) {
            experimentWithSensors.experiment.setSensors(experimentWithSensors.sensors);
            experiments.add(experimentWithSensors.experiment);
        }
        return experiments;
    }

    @Transaction
    @Query("SELECT * FROM "+ Experiment.TABLE_NAME)
    public abstract List<ExperimentWithSensors> loadExperimentsWithSensors();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ Experiment.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    public abstract Experiment selectById(long id);


    @Insert
    public long insert(Experiment register){
        if(register.getConfiguration().getSensorConfig() != null && register.getConfiguration().getSensorConfig().getSensors() != null){
            insertAllSensorForExperiment(register.getConfiguration().getSensorConfig().getSensors(), register);
        }
        return _insert(register);
    }
    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    abstract long _insert(Experiment register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    public abstract int update(Experiment register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + Experiment.TABLE_NAME + " WHERE _id = :id")
    public abstract int deleteById(long id);


    @Insert
    private void insertAllSensorForExperiment(List<MySensor> sensors, Experiment experiment){
        for(MySensor sensor : sensors){
            sensor.setExperimentId(experiment.getInternalId());
        }
        _insertAllSensors(sensors);

    }

    @Insert
    abstract void _insertAllSensors(List<MySensor> sensors);
}
