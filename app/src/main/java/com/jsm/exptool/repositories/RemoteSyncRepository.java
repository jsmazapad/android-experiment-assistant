package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.core.data.network.RetrofitService;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.data.network.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.data.network.RemoteSyncApiService;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteSyncRepository {

    private static final RemoteSyncApiService remoteSyncService = RetrofitService.createService(RemoteSyncApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), BuildConfig.BASE_URL);


    public static void syncExperiment(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, Experiment experiment) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.putExperiment(experiment);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));

    }

    public static void syncImageFile(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, String filename, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("name", file.getName(), requestFile);

        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.uploadImage(experimentId, body);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }

    public static void syncAudioFile(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, String filename, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("name", file.getName(), requestFile);
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.uploadAudio(experimentId, body);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }

    public static void syncImageRegisters(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, List<ImageRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.putImageRegisters(experimentId, registers);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }

    public static void syncAudioRegisters(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, List<AudioRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.putAudioRegisters(experimentId, registers);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }

    public static void syncSensorRegisters(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, List<SensorRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.putSensorRegisters(experimentId, registers);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }

    public static void syncCommentRegisters(MutableLiveData<ElementResponse<RemoteSyncResponse>> responseLiveData, long experimentId, List<CommentRegister> registers) {
        Call<NetworkElementResponse<RemoteSyncResponse>> call = remoteSyncService.putCommentRegisters(experimentId, registers);
        call.enqueue(RetrofitService.createElementCallBack(RemoteSyncResponse.class, responseLiveData));
    }


}
