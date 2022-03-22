package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jsm.exptool.model.register.CommentRegister;

import java.util.List;

@Dao
public interface CommentRegisterDao {

    /**
     * Selecciona todas las entidades de la BD
     * en este caso no realizamos order BY porque queremos un criterio uniforme
     * con otras fuentes de datos y lo ordenamos mediante programación
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME)
    List<CommentRegister> getComments();

    /**
     * Selecciona un registro mediante su id (externo)
     * @param id id de la entidad objetivo
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME + " WHERE _id = :id LIMIT 1")
    CommentRegister selectById(long id);

    /**
     * Selecciona todas las entidades de la BD para un experimento
     * @return
     */
    @Query("SELECT * FROM "+ CommentRegister.TABLE_NAME + " WHERE experimentId = :experimentId ORDER BY date DESC")
    List<CommentRegister> getCommentRegistersByExperimentId(long experimentId);

    /**
     * Inserta un registro
     * @param register
     * @return Id del elemento insertado
     */
    @Insert
    long insert(CommentRegister register);

    /**
     * Actualiza un registro
     * @param register
     * @return num de registros afectados
     */
    @Update
    int update(CommentRegister register);

    /**
     * Elimina un registro usando su id (externo)
     * @param id
     * @return  número de registros eliminados
     */
    @Query("DELETE FROM " + CommentRegister.TABLE_NAME + " WHERE _id = :id")
    int deleteById(long id);
}
