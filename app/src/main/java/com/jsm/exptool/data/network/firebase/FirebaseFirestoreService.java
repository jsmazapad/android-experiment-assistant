package com.jsm.exptool.data.network.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;

public class FirebaseFirestoreClient {
    public enum FirestoreCollections {
        IMAGES("images"),
        AUDIOS("audios"),
        COMMENTS("comments"),
        SENSORS("sensors"),
        EXPERIMENTS("experiments");

        private String name;

        FirestoreCollections(String name) {
            this.name = name;
        }

        public String getType() {
            return this.name;
        }
    }

    public static <T> void putValue(String experimentId, T element, FirebaseFirestoreClient.FirestoreCollections type, NetworkElementResponseCallback<RemoteSyncResponse> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (type.equals(FirestoreCollections.EXPERIMENTS)) {

            if (element instanceof Experiment) {
                Experiment experiment = (Experiment) element;
                CollectionReference reference = db.collection(type.getType());
                DocumentReference docReference = reference.document(String.valueOf(experiment.getExternalId()));
                docReference.set(experiment).addOnCompleteListener(task -> {
                    callback.onResponse(new ElementResponse<>(new RemoteSyncResponse(experiment.getExternalId(), "")));
                }).addOnFailureListener(exception -> {
                    callback.onResponse(new ElementResponse<>(new BaseException(exception.getMessage(), true)));
                });

            } else {
                callback.onResponse(new ElementResponse<>(new BaseException(exception.getMessage(), true)));

            }

            }else{
            CollectionReference reference = db.collection(type.getType());
            DocumentReference docReference = reference.document(experimentId);
            docReference.collection()
        }

        }


    }
