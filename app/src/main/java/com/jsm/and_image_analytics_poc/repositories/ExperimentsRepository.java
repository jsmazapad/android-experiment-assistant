package com.jsm.and_image_analytics_poc.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ElementResponse;
import com.jsm.and_image_analytics_poc.core.data.repositories.responses.ListResponse;
import com.jsm.and_image_analytics_poc.model.Experiment;
import com.jsm.and_image_analytics_poc.model.ImageEmbeddingVector;

import java.util.ArrayList;

public class ExperimentsRepository {

    public static void getExperiments(MutableLiveData<ListResponse<Experiment>> responseLiveData){
        responseLiveData.setValue(new ListResponse<Experiment>(new ArrayList<>()));
    }

}
