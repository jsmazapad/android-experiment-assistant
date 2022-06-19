package com.jsm.exptool.data.network.remotestrategies;

import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.firebase.FirebaseFirestoreService;
import com.jsm.exptool.data.network.firebase.FirebaseStorageService;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;

import java.io.File;
import java.util.List;

import retrofit2.Call;

public class FireBaseStrategy implements RemoteStrategyInterface{
    @Override
    public Call<NetworkElementResponse<LoginResponse>> login(NetworkElementResponseCallback<LoginResponse> callback, boolean returnCall) {
        return null;
    }

    @Override
    public void syncExperiment(NetworkElementResponseCallback<RemoteSyncResponse> callback, Experiment experiment) {
        FirebaseFirestoreService.putValue(null, experiment, FirebaseFirestoreService.FirestoreCollections.EXPERIMENTS, callback );
    }

    @Override
    public void syncImageFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.IMAGE,callback);
    }

    @Override
    public void syncAudioFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.AUDIO,callback);
    }

    @Override
    public void syncImageRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<ImageRegister> registers) {
        FirebaseFirestoreService.putValues(null, registers, FirebaseFirestoreService.FirestoreCollections.IMAGES, callback );

    }

    @Override
    public void syncAudioRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<AudioRegister> registers) {
        FirebaseFirestoreService.putValues(null, registers, FirebaseFirestoreService.FirestoreCollections.AUDIOS, callback );

    }

    @Override
    public void syncSensorRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<SensorRegister> registers) {
        FirebaseFirestoreService.putValues(null, registers, FirebaseFirestoreService.FirestoreCollections.SENSORS, callback );

    }

    @Override
    public void syncCommentRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<CommentRegister> registers) {
        FirebaseFirestoreService.putValues(null, registers, FirebaseFirestoreService.FirestoreCollections.COMMENTS, callback );

    }
}
