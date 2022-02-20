package com.jsm.exptool.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jsm.exptool.model.ImageRegister;


/**
 * Clase que representa a la base de datos
 */
@Database(entities = {ImageRegister.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Obtiene un DAO de la entidad Beer para realizar operaciones con la BD
     * @return
     */
    public abstract ImageRegisterDao imageDao();

}