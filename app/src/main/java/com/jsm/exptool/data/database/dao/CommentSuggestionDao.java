package com.jsm.exptool.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.jsm.exptool.model.CommentSuggestion;

import java.util.List;

@Dao
public abstract class CommentSuggestionDao {


        /**
         * Selecciona todas las entidades de la BD
         * ordenadas por frecuencia de aparición y alfabéticamente
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME +" ORDER BY usedTimesCounter DESC, comment ASC")
        public abstract List<CommentSuggestion> getCommentSuggestions();

        /**
         * Selecciona todas las entidades de la BD
         * ordenadas por frecuencia de aparición y alfabéticamente y filtradas por una cadena
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME +" WHERE comment LIKE :stringToFilter ORDER BY usedTimesCounter DESC, comment ASC")
        public abstract List<CommentSuggestion> getCommentSuggestionsFilterByString(String stringToFilter);

        /**
         * Selecciona un registro mediante su id
         * @param id id de la entidad objetivo
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME + " WHERE _id = :id LIMIT 1")
        public abstract CommentSuggestion selectById(long id);

        /**
         * Selecciona un registro mediante su id
         * @param comment comentario de la entidad objetivo
         * @return
         */
        @Query("SELECT * FROM "+ CommentSuggestion.TABLE_NAME + " WHERE comment = :comment LIMIT 1")
        public abstract CommentSuggestion selectByComment(String comment);


        /**
         * Inserta un registro, si el registro ya se encuentra, aumenta el contador en 1 y lo actualiza
         * @param register
         * @return Id del elemento insertado
         */
        public long insertOrUpdate(CommentSuggestion register){
           CommentSuggestion relatedComment = selectByComment(register.getComment());
           if(relatedComment != null){
                   relatedComment.setUsedTimesCounter(relatedComment.getUsedTimesCounter()+1);
                   return (long)update(relatedComment);
           }else{
                   return insert(register);
           }
        }
        /**
         * Inserta un registro
         * @param register
         * @return Id del elemento insertado
         */
        @Insert
        public abstract long insert(CommentSuggestion register);

        /**
         * Actualiza un registro
         * @param register
         * @return num de registros afectados
         */
        @Update
        public abstract int update(CommentSuggestion register);

        /**
         * Elimina un registro usando su id (externo)
         * @param id
         * @return  número de registros eliminados
         */
        @Query("DELETE FROM " + CommentSuggestion.TABLE_NAME + " WHERE _id = :id")
        public abstract int deleteById(long id);

        @Query("UPDATE " + CommentSuggestion.TABLE_NAME + " SET usedTimesCounter = 0")
        public abstract long resetSuggestionsCounter();

}
