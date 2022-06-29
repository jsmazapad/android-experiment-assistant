package com.jsm.exptool.data.network.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;

import java.io.File;

public class FirebaseStorageService {

    public enum FileTypes {
        IMAGE("image"),
        AUDIO("audio");

        private String type;

        FileTypes(String type) {
            this.type = type;
        }

        public String getType(){
            return this.type;
        }
    }

    public static void uploadFile(String externalId, File file, FileTypes type, NetworkElementResponseCallback<RemoteSyncResponse> callback){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(String.format("%s/%s/%s", externalId, type.getType(), file.getName()));
        storageRef.putFile(Uri.fromFile(file)).addOnCompleteListener(task -> {
            callback.onResponse(new ElementResponse<>(new RemoteSyncResponse("", "")));
        }).addOnFailureListener(exception->{
            callback.onResponse(new ElementResponse<>(new BaseException(exception.getMessage(), true)));
        });
    }

}
