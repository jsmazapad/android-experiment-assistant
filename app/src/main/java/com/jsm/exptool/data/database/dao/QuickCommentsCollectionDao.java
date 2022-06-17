package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.entities.QuickCommentsCollection;

import java.util.List;

@Dao
public interface QuickCommentsCollectionDao {

    /**
     * Selecciona todas las entidades de la BD
     * ordenadas por frecuencia de aparición y alfabéticamente
     * @return
     */
    @Query("SELECT * FROM "+ QuickCommentsCollection.TABLE_NAME +" ORDER BY name ASC")
    List<QuickCommentsCollection> getQuickCommentsCollections();



    /**
     * Selecciona un registro mediante su id
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ QuickCommentsCollection.TABLE_NAME + " WHERE _id = :id LIMIT 1")
    QuickCommentsCollection selectById(long id);



    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(QuickCommentsCollection register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(QuickCommentsCollection register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + QuickCommentsCollection.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);
}
