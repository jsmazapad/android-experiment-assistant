package com.jsm.exptool.data.network.api;

import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RemoteSyncApiService {

//    //pass it like this
//    File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
//    RequestBody requestFile =
//            RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//    // MultipartBody.Part is used to send also the actual file name
//    MultipartBody.Part body =
//            MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//
//    // add another part within the multipart request
//    RequestBody fullName =
//            RequestBody.create(MediaType.parse("multipart/form-data"), "Your Name");

    @Multipart
    @PUT("experiment/{experimentId}/image/add")
        //@Headers("Content-Type: image/jpeg")
    Call<NetworkElementResponse<RemoteSyncResponse>> uploadImage(@Path("experimentId") String experimentId, @Part MultipartBody.Part file);

    @Multipart
    @PUT("experiment/{experimentId}/audio/add")
    Call<NetworkElementResponse<RemoteSyncResponse>> uploadAudio(@Path("experimentId") String experimentId,  @Part MultipartBody.Part file);

    @Headers("Content-Type: application/json")
    @PUT("experiment/add")
    Call<NetworkElementResponse<RemoteSyncResponse>> putExperiment(@Body Experiment experiment);

    @Headers("Content-Type: application/json")
    @PUT("experiment/{experimentId}/imageRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putImageRegisters(@Path("experimentId") String experimentId, @Body List<ImageRegister> registers);

    @Headers("Content-Type: application/json")
    @PUT("experiment/{experimentId}/audioRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putAudioRegisters(@Path("experimentId") String experimentId, @Body List<AudioRegister> registers);

    @Headers("Content-Type: application/json")
    @PUT("experiment/{experimentId}/sensorRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putSensorRegisters(@Path("experimentId") String experimentId, @Body List<SensorRegister> registers);

    @Headers("Content-Type: application/json")
    @PUT("experiment/{experimentId}/commentRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putCommentRegisters(@Path("experimentId") String experimentId, @Body List<CommentRegister> registers);

    @FormUrlEncoded
    @POST("login")
    Call<NetworkElementResponse<LoginResponse>> login(@Field("user") String user, @Field("password") String password);

}
