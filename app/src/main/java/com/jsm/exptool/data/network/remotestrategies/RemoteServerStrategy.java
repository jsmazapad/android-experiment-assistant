package com.jsm.exptool.data.network.remotestrategies;

import com.jsm.exptool.core.data.network.HttpClientServiceManager;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.api.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.data.network.api.RemoteSyncApiService;
import com.jsm.exptool.data.network.api.interceptors.AuthorizationInterceptor;
import com.jsm.exptool.data.network.api.interceptors.HeaderInterceptor;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.PreferencesProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteServerStrategy implements RemoteStrategyInterface {
    
    private  RemoteSyncApiService getRemoteSyncService(boolean withInterceptors){
        List<Interceptor> interceptors =null;
        if(withInterceptors) {
            interceptors = new ArrayList<>();
            interceptors.add(new HeaderInterceptor());
            interceptors.add(new AuthorizationInterceptor());
        }
        return HttpClientServiceManager.createService(RemoteSyncApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), interceptors, PreferencesProvider.getRemoteServer(), true);
    }

    public  Call<NetworkElementResponse<LoginResponse>> login(NetworkElementResponseCallback<LoginResponse> callback, boolean returnCall) {
        Call<NetworkElementResponse<LoginResponse>> call = getRemoteSyncService(false).login(PreferencesProvider.getUser(), PreferencesProvider.getPassword());
        if (returnCall){
            return call;
        }else {
            call.enqueue(HttpClientServiceManager.createElementCallBack(LoginResponse.class, callback));
            return null;
        }

    }


    public  void syncExperiment(NetworkElementResponseCallback<RemoteSyncResponse> callback, Experiment experiment) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).putExperiment(experiment);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));

    }

    public  void syncImageFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("name", file.getName(), requestFile);

        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).uploadImage(experimentId, body);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }

    public  void syncAudioFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("name", file.getName(), requestFile);
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).uploadAudio(experimentId, body);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }

    public  void syncImageRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<ImageRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).putImageRegisters(experimentId, registers);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }

    public  void syncAudioRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<AudioRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).putAudioRegisters(experimentId, registers);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }

    public  void syncSensorRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<SensorRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).putSensorRegisters(experimentId, registers);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }

    public  void syncCommentRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<CommentRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = getRemoteSyncService(true).putCommentRegisters(experimentId, registers);
        call.enqueue(HttpClientServiceManager.createElementCallBack(RemoteSyncResponse.class, callback));
    }
}
