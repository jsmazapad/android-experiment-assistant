package com.jsm.exptool.repositories;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.RetrofitService;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.data.network.AnalyticsApiService;
import com.jsm.exptool.data.network.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.model.embedding.ImageEmbeddingVector;
import com.jsm.exptool.model.ImageRegister;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class ImagesRepository {
    private static final AnalyticsApiService ImageEmbeddingService = RetrofitService.createService(AnalyticsApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), BuildConfig.BASE_URL);

    /**
     * Obtiene el vector de embedding de manera reactiva
     *
     * @param responseLiveData livedata donde se setean los elementos
     * @param image            Fichero que contiene una imagen
     */
    public static void getEmbedding(MutableLiveData<ElementResponse<ImageEmbeddingVector>> responseLiveData, File image, String algorithm) {

        InputStream in;
        try {
            in = new FileInputStream(image);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1);
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);
            Call<NetworkElementResponse<ImageEmbeddingVector>> call = ImageEmbeddingService.getEmbedding(algorithm, requestBody);
            call.enqueue(RetrofitService.createElementCallBack(ImageEmbeddingVector.class, responseLiveData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            responseLiveData.setValue(new ElementResponse<>(new BaseException(e.getMessage(), false)));

        } catch (IOException e) {
            e.printStackTrace();
            responseLiveData.setValue(new ElementResponse<>(new BaseException(e.getMessage(), false)));

        }
    }

    public static ImageRegister getImageRegisterById(long imageId){
        return DBHelper.getImageById(imageId);
    }

    public static void getEmbedding(NetworkElementResponseCallback<ImageEmbeddingVector> callback, File image, String algorithm){

        InputStream in = null;
        try {
            in = new FileInputStream(image);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1);
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);
            Call<NetworkElementResponse<ImageEmbeddingVector>> call = ImageEmbeddingService.getEmbedding(algorithm, requestBody);
            call.enqueue(RetrofitService.createElementCallBack(ImageEmbeddingVector.class, callback));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onResponse(new ElementResponse<>(new BaseException(e.getMessage(), false)));
        } catch (IOException e) {
            e.printStackTrace();
            callback.onResponse(new ElementResponse<>(new BaseException(e.getMessage(), false)));
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long registerImageCapture(File imageFile, long experimentId, Date date){
        ImageRegister imageRegister = new ImageRegister(imageFile.getName(), imageFile.getParent(), new ArrayList<>(), false, false, false, experimentId, date);
        return DBHelper.insertImageRegister(imageRegister);

    }
    public static int registerImageEmbedding(ImageRegister imageRegister, ImageEmbeddingVector vector){
        imageRegister.setEmbedding(vector.getEmbedding());
        return DBHelper.updateImage(imageRegister);
    }
}
