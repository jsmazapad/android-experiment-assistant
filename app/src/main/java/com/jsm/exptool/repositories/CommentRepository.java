package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.register.CommentRegister;

public class CommentRepository {

    public static void getCommentRegisters(MutableLiveData<ListResponse<CommentRegister>> responseLiveData, long experimentId){
        responseLiveData.setValue(new ListResponse<>(DBHelper.getCommentRegistersByExperimentId(experimentId)));
    }

    public static long registerComment(CommentRegister comment){
        return DBHelper.insertCommentRegister(comment);
    }

}
