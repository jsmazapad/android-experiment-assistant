package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.data.database.relations.ExperimentWithSensors;
import com.jsm.exptool.data.database.typeconverters.ExperimentStatusConverter;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.model.register.AudioRegister;

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
     *
     * @return
     */

    @Query("SELECT * FROM " + Experiment.TABLE_NAME + " ORDER BY initDate")
    public abstract List<Experiment> getExperiments();

    @Query("SELECT * FROM " + Experiment.TABLE_NAME + " WHERE status = :status ORDER BY initDate")
    public abstract List<Experiment> getExperimentsFilteredByState(int status);

    public List<Experiment> getExperimentsWithSensors(Experiment.ExperimentStatus statusFilter) {
        List<ExperimentWithSensors> experimentsWithSensors;
        if (statusFilter == null) {
            experimentsWithSensors = loadExperimentsWithSensors();
        } else {
            experimentsWithSensors = loadExperimentsWithSensorsFilteredByState(ExperimentStatusConverter.fromEnum(statusFilter));
        }

        List<Experiment> experiments = new ArrayList<>(experimentsWithSensors.size());
        for (ExperimentWithSensors experimentWithSensors : experimentsWithSensors) {
            if (experimentWithSensors.experiment.getConfiguration().getSensorConfig() == null) {
                experimentWithSensors.experiment.getConfiguration().setSensorConfig(new SensorsGlobalConfig(FrequencyConstants.DEFAULT_SENSOR_FREQ, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
            }
            experimentWithSensors.experiment.getConfiguration().getSensorConfig().setSensors(experimentWithSensors.sensors);
            experiments.add(experimentWithSensors.experiment);
        }
        return experiments;
    }

    @Transaction
    @Query("SELECT * FROM " + Experiment.TABLE_NAME + " ORDER BY initDate")
    public abstract List<ExperimentWithSensors> loadExperimentsWithSensors();

    @Transaction
    @Query("SELECT * FROM " + Experiment.TABLE_NAME + " WHERE status = :status ORDER BY initDate")
    public abstract List<ExperimentWithSensors> loadExperimentsWithSensorsFilteredByState(int status);

    /**
     * Selecciona un registro mediante su id (externo)
     *
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM " + Experiment.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    public abstract Experiment selectById(long id);


    @Insert
    public long insert(Experiment register) {
        long id = _insert(register);
        if (id > -1) {
            register.setInternalId(id);
            if (register.getConfiguration().getSensorConfig() != null && register.getConfiguration().getSensorConfig().getSensors() != null) {
                insertAllSensorForExperiment(register.getConfiguration().getSensorConfig().getSensors(), register);
            }
        }
        return id;
    }

    /**
     * Inserta un registro
     *
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    abstract long _insert(Experiment register);

    /**
     * Actualiza un registro
     *
     * @param register
     * @return num de registros afectados
     */
    @Update
    public abstract int update(Experiment register);

    /**
     * Elimina un registro y su configuración de sensores asociada usando su id (externo)
     *
     * @param id
     * @return número de registros eliminados
     */
    public int deleteById(long id) {
        _deleteSensorConfigByExperimentId(id);
        return _deleteById(id);
    }

    /**
     * Elimina un registro usando su id (externo)
     *
     * @param id
     * @return número de registros eliminados
     */
    @Query("DELETE FROM " + Experiment.TABLE_NAME + " WHERE _id = :id")
    public abstract int _deleteById(long id);

    /**
     * Elimina los registros asociado a un experimentId
     *
     * @param id
     * @return número de registros eliminados
     */
    @Query("DELETE FROM " + SensorConfig.TABLE_NAME + " WHERE experimentId = :id")
    public abstract int _deleteSensorConfigByExperimentId(long id);


    @Insert
    private void insertAllSensorForExperiment(List<SensorConfig> sensors, Experiment experiment) {

        for (SensorConfig sensor : sensors) {
            sensor.setExperimentId(experiment.getInternalId());
        }
        _insertAllSensors(sensors);

    }

    @Insert
    abstract void _insertAllSensors(List<SensorConfig> sensors);
}
