package com.jsm.exptool.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.Experiment;

import java.util.List;


/**
 * DAO para realizar operaciones con la BD,
 * a partir de esta interface se autogenera el código necesario
 */
@Dao
public interface ExperimentDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ Experiment.TABLE_NAME)
    List<Experiment> getExperiments();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ Experiment.TABLE_NAME + " WHERE _id" + " = :id LIMIT 1")
    Experiment selectById(long id);


    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(Experiment register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(Experiment register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + Experiment.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);
}
