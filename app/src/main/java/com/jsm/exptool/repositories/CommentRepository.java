package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommentRepository {

    public static void getCommentRegisters(MutableLiveData<ListResponse<CommentRegister>> responseLiveData, long experimentId) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.postValue(new ListResponse<>(DBHelper.getCommentRegistersByExperimentId(experimentId))));
    }

    public static void getRegistersByExperimentIdAsExperimentRegister(long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.setValue(new ListResponse<>(new ArrayList<ExperimentRegister>() {{
            addAll(DBHelper.getCommentRegistersByExperimentId(experimentId));
        }})));
    }

    public static long registerComment(CommentRegister comment) {
        return DBHelper.insertCommentRegister(comment);
    }

    public static void countRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.getCommentRegistersByExperimentId(experimentId).size()));

    }

}
