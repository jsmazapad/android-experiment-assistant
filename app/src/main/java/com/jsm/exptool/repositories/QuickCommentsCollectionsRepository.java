package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.QuickCommentsCollection;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuickCommentsCollectionsRepository {

    /**
     * Devuelve las colecciones de comentarios ordenadas alfabéticamente
     * @param responseLiveData
     */
    public static void getQuickCommentsCollections(MutableLiveData<ListResponse<QuickCommentsCollection>> responseLiveData){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute( () -> responseLiveData.postValue(new ListResponse<>(DBHelper.getQuickCommentsCollections())));
    }


    public static long registerQuickCommentsCollection(QuickCommentsCollection quickCommentsCollection){
        return DBHelper.insertQuickCommentsCollection(quickCommentsCollection);
    }

    public static long updateQuickCommentsCollection(QuickCommentsCollection quickCommentsCollection){
        return DBHelper.updateQuickCommentsCollection(quickCommentsCollection);
    }

    public static long deleteQuickCommentsCollection(QuickCommentsCollection quickCommentsCollection){
        return DBHelper.deleteQuickCommentsCollectionById(quickCommentsCollection);
    }

}
