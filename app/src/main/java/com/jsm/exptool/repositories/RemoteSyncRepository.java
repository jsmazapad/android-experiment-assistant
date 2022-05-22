package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.core.data.network.RetrofitService;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.data.network.AnalyticsApiService;
import com.jsm.exptool.data.network.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.data.network.RemoteSyncApiService;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.embedding.ImageEmbeddingVector;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.List;

public class RemoteSyncRepository {

    private static final RemoteSyncApiService remoteSyncService = RetrofitService.createService(RemoteSyncApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), BuildConfig.BASE_URL);


    public static void syncExperiment (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, Experiment experiment){

    }

    public static void syncImageFile (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, ImageRegister register){

    }

    public static void syncAudioFile (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, AudioRegister register){

    }

    public static void syncImageRegisters (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, List<ImageRegister> registers){

    }

    public static void syncAudioRegisters (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, List<AudioRegister> registers){

    }

    public static void syncSensorRegisters (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, List<SensorRegister> registers){

    }

    public static void syncCommentRegisters (MutableLiveData<ElementResponse<String>> responseLiveData, long experimentId, List<SensorRegister> registers){

    }


}
