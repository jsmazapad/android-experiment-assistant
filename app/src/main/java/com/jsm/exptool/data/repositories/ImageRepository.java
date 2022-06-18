package com.jsm.exptool.data.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.HttpClientServiceManager;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.data.network.AnalyticsApiService;
import com.jsm.exptool.data.network.AppDeserializerProvider;
import com.jsm.exptool.data.network.AppNetworkErrorTreatment;
import com.jsm.exptool.entities.embedding.ImageEmbeddingVector;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.entities.register.ImageRegister;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class ImageRepository {
    private static final AnalyticsApiService imageEmbeddingService = HttpClientServiceManager.createService(AnalyticsApiService.class, new AppNetworkErrorTreatment(), new AppDeserializerProvider(), null,  BuildConfig.BASE_URL_EMBEDDING_SERVER, false);

    public static void getEmbedding(NetworkElementResponseCallback<ImageEmbeddingVector> callback, File image, String algorithm) {

        InputStream in = null;
        try {
            in = new FileInputStream(image);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1) ;
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);
            Call<NetworkElementResponse<ImageEmbeddingVector>> call = imageEmbeddingService.getEmbedding(algorithm, requestBody);
            call.enqueue(HttpClientServiceManager.createElementCallBack(ImageEmbeddingVector.class, callback));
        } catch (FileNotFoundException e) {
            Log.w(ImageRepository.class.getSimpleName(), e.getMessage(), e);
            callback.onResponse(new ElementResponse<>(new BaseException(e.getMessage(), false)));
        } catch (IOException e) {
            Log.w(ImageRepository.class.getSimpleName(), e.getMessage(), e);
            callback.onResponse(new ElementResponse<>(new BaseException(e.getMessage(), false)));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.w(ImageRepository.class.getSimpleName(), e.getMessage(), e);
                }
            }
        }
    }

    public static long registerImageCapture(File imageFile, long experimentId, Date date) {
        ImageRegister imageRegister = new ImageRegister(imageFile.getName(), imageFile.getParent(), new ArrayList<>(), false, false, false, experimentId, date);
        return DBHelper.insertImageRegister(imageRegister);

    }

    public static int registerImageEmbedding(ImageRegister imageRegister, ImageEmbeddingVector vector) {
        imageRegister.setEmbedding(vector.getEmbedding());
        imageRegister.setEmbeddingRemoteSynced(false);
        return DBHelper.updateImageRegister(imageRegister);
    }

    public static int updateImageRegister(ImageRegister imageRegister) {
        return DBHelper.updateImageRegister(imageRegister);
    }

    public static int updateRegisterFileSyncedByRegisterId(long registerId){
        return DBHelper.updateImageRegisterFileSyncedByRegisterId(registerId);
    }


    public static void getRegistersByExperimentIdAsExperimentRegister(long experimentId, MutableLiveData<ListResponse<ExperimentRegister>> responseLiveData) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> responseLiveData.postValue(new ListResponse<>(new ArrayList<>(
            DBHelper.getImageRegistersByExperimentId(experimentId))
        )));

    }

    public static List<ImageRegister> getSynchronouslyPendingSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingSyncImageRegistersByExperimentId(experimentId);
    }

    public static List<ImageRegister> getSynchronouslyPendingFileSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingFileSyncImageRegistersByExperimentId(experimentId);
    }

    public static List<ImageRegister> getSynchronouslyPendingEmbeddingSyncRegistersByExperimentId(long experimentId) {
        return DBHelper.getPendingEmbeddingImageRegistersByExperimentId(experimentId);
    }

    public static ImageRegister getSynchronouslyRegisterById(long imageId) {
        return DBHelper.getImageById(imageId);
    }

    public static void countRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countImageRegistersByExperimentId(experimentId)));
    }

    public static void countImagesWithEmbeddingsByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countImageRegistersWithEmbeddingByExperimentId(experimentId)));
    }

    public static void countPendingSyncRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingSyncImageRegistersByExperimentId(experimentId)));
    }

    public static void countPendingSyncFileRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingFileSyncImageRegistersByExperimentId(experimentId)));
    }

    public static void countPendingSyncEmbeddingRegistersByExperimentId(long experimentId, MutableLiveData<Integer> countResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> countResponse.postValue(DBHelper.countPendingEmbeddingImageRegistersByExperimentId(experimentId)));
    }
}
