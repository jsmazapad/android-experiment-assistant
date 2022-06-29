package com.jsm.exptool.data.network.remotestrategies;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.firebase.FirebaseFirestoreService;
import com.jsm.exptool.data.network.firebase.FirebaseStorageService;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.SessionProvider;

import java.io.File;
import java.util.List;

import retrofit2.Call;

public class FireBaseStrategy implements RemoteStrategyInterface{

    public static void init(Context c){

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(PreferencesProvider.getFirebaseKey())
                .setApplicationId(PreferencesProvider.getFirebaseApp())
                .setProjectId(PreferencesProvider.getFirebaseProject())
                .setStorageBucket(PreferencesProvider.getFirebaseStorageBucket()).build();
        FirebaseApp.initializeApp(c, options);
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user == null){
                SessionProvider.clearSession();
            }else{
                SessionProvider.setToken("FIREBASE");
            }
        });
    }

    @Override
    public Call<NetworkElementResponse<LoginResponse>> login(NetworkElementResponseCallback<LoginResponse> callback, boolean returnCall) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(PreferencesProvider.getUser(), PreferencesProvider.getPassword()).addOnSuccessListener(authResult -> callback.onResponse(new ElementResponse<>(new LoginResponse("OK")))).addOnFailureListener(e -> callback.onResponse(new ElementResponse<>(new BaseException(e.getMessage(), false))));
        return null;
    }

    @Override
    public void syncExperiment(NetworkElementResponseCallback<RemoteSyncResponse> callback, Experiment experiment) {
        if(SessionProvider.hasSession()) {
            FirebaseFirestoreService.putValue(null, experiment, FirebaseFirestoreService.FirestoreCollections.EXPERIMENTS, callback);
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseFirestoreService.putValue(null, experiment, FirebaseFirestoreService.FirestoreCollections.EXPERIMENTS, callback);
                }

            }, false);
        }
    }

    @Override
    public void syncImageFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        if(SessionProvider.hasSession()) {
            FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.IMAGE,callback);
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.IMAGE,callback);
                }

            }, false);
        }
    }

    @Override
    public void syncAudioFile(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, File file) {
        if(SessionProvider.hasSession()) {
            FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.AUDIO,callback);
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseStorageService.uploadFile(experimentId, file, FirebaseStorageService.FileTypes.AUDIO,callback);
                }

            }, false);
        }
    }

    @Override
    public void syncImageRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<ImageRegister> registers) {
        if(SessionProvider.hasSession()) {
            FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.IMAGES, callback );
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.IMAGES, callback );
                }

            }, false);
        }

    }

    @Override
    public void syncAudioRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<AudioRegister> registers) {
        if(SessionProvider.hasSession()) {
            FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.AUDIOS, callback );
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.AUDIOS, callback );
                }

            }, false);
        }
    }

    @Override
    public void syncSensorRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<SensorRegister> registers) {
        if(SessionProvider.hasSession()) {
            FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.SENSORS, callback );
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.SENSORS, callback );
                }

            }, false);
        }

    }

    @Override
    public void syncCommentRegisters(NetworkElementResponseCallback<RemoteSyncResponse> callback, String experimentId, List<CommentRegister> registers) {
        if(SessionProvider.hasSession()) {
            FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.COMMENTS, callback );
        }else{
            login(response -> {
                if(response.getError() != null){
                    callback.onResponse(new ElementResponse<>(response.getError()));
                }else{
                    FirebaseFirestoreService.putValues(experimentId, registers, FirebaseFirestoreService.FirestoreCollections.COMMENTS, callback );
                }

            }, false);
        }

    }
}
