package com.jsm.exptool.data.network;

import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.embedding.ImageEmbeddingVector;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<NetworkElementResponse<RemoteSyncResponse>> uploadImage(@Path("experimentId") long experimentId, @Part MultipartBody.Part file);

    @Multipart
    @PUT("experiment/{experimentId}/audio/add")
    Call<NetworkElementResponse<RemoteSyncResponse>> uploadAudio(@Path("experimentId") long experimentId,  @Part MultipartBody.Part file);

    @PUT("experiment/add")
    Call<NetworkElementResponse<RemoteSyncResponse>> putExperiment(@Body Experiment experiment);

    @PUT("experiment/{experimentId}/imageRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putImageRegisters(@Path("experimentId") long experimentId, @Body List<ImageRegister> registers);

    @PUT("experiment/{experimentId}/audioRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putAudioRegisters(@Path("experimentId") long experimentId, @Body List<AudioRegister> registers);

    @PUT("experiment/{experimentId}/sensorRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putSensorRegisters(@Path("experimentId") long experimentId, @Body List<SensorRegister> registers);

    @PUT("experiment/{experimentId}/commentRegister/addAll")
    Call<NetworkElementResponse<RemoteSyncResponse>> putCommentRegisters(@Path("experimentId") long experimentId, @Body List<CommentRegister> registers);

}
