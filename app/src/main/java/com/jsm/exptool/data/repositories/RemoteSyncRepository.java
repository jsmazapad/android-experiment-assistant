package com.jsm.exptool.data.repositories;

import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.HttpClientServiceManager;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.data.network.api.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.data.network.api.RemoteSyncApiService;
import com.jsm.exptool.data.network.api.interceptors.AuthorizationInterceptor;
import com.jsm.exptool.data.network.api.interceptors.HeaderInterceptor;
import com.jsm.exptool.data.network.exceptions.InvalidRemoteStrategyException;
import com.jsm.exptool.data.network.remotestrategies.RemoteStrategyInterface;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.RemoteSyncMethodsProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteSyncRepository {


    public static Call<NetworkElementResponse<LoginResponse>> login(NetworkElementResponseCallback<LoginResponse> callback, boolean returnCall) {
       RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            return strategy.login(callback, returnCall);
        }else{
            if(!returnCall) {
                callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
            }
            return null;
        }

    }


    public static void syncExperiment(NetworkElementResponseCallback<RemoteSyncResponse> callback, Experiment experiment) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
             strategy.syncExperiment(callback, experiment);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncImageFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncImageFile(callback, experimentId, file);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncAudioFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncAudioFile(callback, experimentId, file);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncImageRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<ImageRegister> registers) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncImageRegisters(callback, experimentId, registers);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncAudioRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<AudioRegister> registers) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncAudioRegisters(callback, experimentId, registers);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncSensorRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<SensorRegister> registers) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncSensorRegisters(callback, experimentId, registers);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }

    public static void syncCommentRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<CommentRegister> registers) {
        RemoteStrategyInterface strategy = RemoteSyncMethodsProvider.getRemoteStrategy();
        if(strategy != null){
            strategy.syncCommentRegisters(callback, experimentId, registers);
        }else {
            callback.onResponse(new ElementResponse<>(new InvalidRemoteStrategyException()));
        }
    }


}
