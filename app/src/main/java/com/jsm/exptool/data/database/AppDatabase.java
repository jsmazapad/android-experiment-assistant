package com.jsm.exptool.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jsm.exptool.data.database.dao.AudioRegisterDao;
import com.jsm.exptool.data.database.dao.ImageRegisterDao;
import com.jsm.exptool.data.database.dao.ExperimentDao;
import com.jsm.exptool.data.database.typeconverters.CameraPositionsConverter;
import com.jsm.exptool.data.database.typeconverters.DateConverter;
import com.jsm.exptool.data.database.typeconverters.DoubleListConverter;
import com.jsm.exptool.data.database.typeconverters.ExperimentStatusConverter;
import com.jsm.exptool.data.database.typeconverters.FlashModesConverter;
import com.jsm.exptool.data.database.typeconverters.IntegerListConverter;
import com.jsm.exptool.model.AudioRegister;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.ImageRegister;
import com.jsm.exptool.model.MySensor;


/**
 * Clase que representa a la base de datos
 */

@Database(entities = {ImageRegister.class, Experiment.class, AudioRegister.class, MySensor.class}, version = 1)
@TypeConverters({DoubleListConverter.class, IntegerListConverter.class, DateConverter.class, ExperimentStatusConverter.class, CameraPositionsConverter.class, FlashModesConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Obtiene un DAO de la entidad ImageRegister para realizar operaciones con la BD
     * @return
     */
    public abstract ImageRegisterDao imageDao();
    /**
     * Obtiene un DAO de la entidad AudioRegister para realizar operaciones con la BD
     * @return
     */
    public abstract AudioRegisterDao audioDao();
    /**
     * Obtiene un DAO de la entidad Experiment para realizar operaciones con la BD
     * @return
     */
    public abstract ExperimentDao experimentDao();

}