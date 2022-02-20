package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.core.data.network.RetrofitService;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.data.network.AnalyticsApiService;
import com.jsm.exptool.data.network.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.model.ImageEmbeddingVector;
import com.jsm.exptool.model.ImageRegister;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class EmbeddingRepository {
    private static final AnalyticsApiService ImageEmbeddingService = RetrofitService.createService(AnalyticsApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), BuildConfig.BASE_URL);

    /**
     * Obtiene los elementos del menú de manera reactiva
     *
     * @param responseLiveData livedata donde se setean los elementos
     * @param image            Fichero que contiene una imagen
     */
    public static void getEmbedding(MutableLiveData<ElementResponse<ImageEmbeddingVector>> responseLiveData, File image) {

        InputStream in;
        try {
            in = new FileInputStream(image);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1);
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);
            Call<NetworkElementResponse<ImageEmbeddingVector>> call = ImageEmbeddingService.getEmbedding("inception-v3", requestBody);
            call.enqueue(RetrofitService.createElementCallBack(ImageEmbeddingVector.class, responseLiveData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void insertImageEmbedding(File imageFile, ImageEmbeddingVector vector){
        ImageRegister imageRegister = new ImageRegister(imageFile.getName(), vector.getEmbedding(), false);
        //TODO Refactorizar para quitar livedata
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        DBHelper.insertImageRegister(imageRegister, result);

    }
}
