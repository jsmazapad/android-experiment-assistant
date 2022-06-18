package com.jsm.exptool.data.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.entities.CommentSuggestion;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommentSuggestionsRepository {

    /**
     * Devuelve las sugerencias para comentarios ordenadas por uso y alfabéticamente
     * @param responseLiveData
     * @param stringToSearch Usar null para que devuelva todas
     */
    public static void getCommentSuggestions(MutableLiveData<ListResponse<CommentSuggestion>> responseLiveData, @Nullable String stringToSearch){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute( () -> responseLiveData.postValue(new ListResponse<>(DBHelper.getCommentSuggestions(stringToSearch))));
    }


    public static long registerCommentSuggestion(CommentSuggestion commentSuggestion){
        return DBHelper.insertCommentSuggestion(commentSuggestion);
    }

    public static long registerOrUpdateCommentSuggestion(CommentSuggestion commentSuggestion){
        return DBHelper.insertOrUpdateCommentSuggestion(commentSuggestion);
    }

    public static long deleteCommentSuggestion(CommentSuggestion commentSuggestion){
        return DBHelper.deleteCommentSuggestionById(commentSuggestion);
    }

    public static long resetSuggestionsCounter(){
        return DBHelper.resetSuggestionsCounter();
    }

    public static List<CommentSuggestion> checkIfCommentSuggestionExists(String stringToSearch) {
        return DBHelper.checkIfCommentSuggestionExists(stringToSearch);
    }

    public static CommentSuggestion selectCommentSuggestionByComment(String comment) {
        return DBHelper.selectCommentSuggestionByComment(comment);
    }
    public static int updateCommentSuggestion(CommentSuggestion commentSuggestion) {
        return DBHelper.updateCommentSuggestion(commentSuggestion);
    }


}
