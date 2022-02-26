package com.jsm.exptool.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.ImageRegister;


/**
 * Clase que representa a la base de datos
 */

@Database(entities = {ImageRegister.class, Experiment.class}, version = 1)
@TypeConverters({ListConverter.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Obtiene un DAO de la entidad ImageRegister para realizar operaciones con la BD
     * @return
     */
    public abstract ImageRegisterDao imageDao();
    /**
     * Obtiene un DAO de la entidad Experiment para realizar operaciones con la BD
     * @return
     */
    public abstract ExperimentDao experimentDao();

}