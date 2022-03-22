package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.jsm.exptool.model.CommentSuggestion;

import java.util.List;

@Dao
public interface CommentSuggestionDao {


        /**
         * Selecciona todas las entidades de la BD
         * ordenadas por frecuencia de aparición y alfabéticamente
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME +" ORDER BY usedTimesCounter DESC, comment ASC")
        List<CommentSuggestion> getCommentSuggestions();

        /**
         * Selecciona todas las entidades de la BD
         * ordenadas por frecuencia de aparición y alfabéticamente y filtradas por una cadena
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME +" WHERE comment LIKE :stringToFilter ORDER BY usedTimesCounter DESC, comment ASC")
        List<CommentSuggestion> getCommentSuggestionsFilterByString(String stringToFilter);

        /**
         * Selecciona un registro mediante su id
         * @param id id de la entidad objetivo
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME + " WHERE _id = :id LIMIT 1")
        CommentSuggestion selectById(long id);



        /**
         * Inserta un registro
         * @param register
         * @return Id del elemento insertado
         */
        @Insert
        long insert(CommentSuggestion register);

        /**
         * Actualiza un registro
         * @param register
         * @return num de registros afectados
         */
        @Update
        int update(CommentSuggestion register);

        /**
         * Elimina un registro usando su id (externo)
         * @param id
         * @return  número de registros eliminados
         */
        @Query("DELETE FROM " + CommentSuggestion.TABLE_NAME + " WHERE _id = :id")
        int deleteById(long id);

        @Query("UPDATE " + CommentSuggestion.TABLE_NAME + " SET usedTimesCounter = 0")
        long resetSuggestionsCounter();

}
