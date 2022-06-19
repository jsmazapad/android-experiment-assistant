package com.jsm.exptool.data.network;

import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.entities.embedding.ImageEmbeddingVector;

import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Interface con la definición de todas las llamadas al servicio web
 */
public interface AnalyticsApiService {

    /**
     *
     * @param algorithm Algoritmo que se utilizará
     * @return Objeto que representa la llamada al servicio web
     */
    //@Multipart
    @POST("image/{alg}")
    //@Headers("Content-Type: image/jpeg")
    Call<NetworkElementResponse<ImageEmbeddingVector>> getEmbedding(@Path("alg") String algorithm, @Body RequestBody image);

}
