package com.jsm.exptool.data.network.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.firebase.exceptions.FirebaseTypeException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.List;

public class FirebaseFirestoreService {
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

    public static <T> void putValue(String experimentId, T element, FirebaseFirestoreService.FirestoreCollections type, NetworkElementResponseCallback<RemoteSyncResponse> callback) {
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
                callback.onResponse(new ElementResponse<>(new FirebaseTypeException()));

            }

        } else if (element instanceof ExperimentRegister) {
            long id = ((ExperimentRegister) element).getInternalId();
            CollectionReference reference = db.collection(FirestoreCollections.EXPERIMENTS.getType());
            DocumentReference docReference = reference.document(experimentId);
            docReference.collection(type.getType()).document(String.valueOf(id)).set(element).addOnCompleteListener(task -> {
                callback.onResponse(new ElementResponse<>(new RemoteSyncResponse(String.valueOf(id), "")));
            }).addOnFailureListener(exception -> {
                callback.onResponse(new ElementResponse<>(new BaseException(exception.getMessage(), true)));
            });
        } else {
            callback.onResponse(new ElementResponse<>(new FirebaseTypeException()));

        }

    }

    public static <T extends ExperimentRegister> void putValues(String experimentId, List<T> elements, FirebaseFirestoreService.FirestoreCollections type, NetworkElementResponseCallback<RemoteSyncResponse> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WriteBatch batch = db.batch();
        CollectionReference reference = db.collection(FirestoreCollections.EXPERIMENTS.getType());
        DocumentReference docReference = reference.document(experimentId);
        for (T register : elements) {
            String id = String.valueOf(register.getInternalId());
            batch.set(docReference.collection(type.getType()).document(id), register);
        }
        batch.commit().addOnCompleteListener(task -> {
            callback.onResponse(new ElementResponse<>(new RemoteSyncResponse(experimentId, "")));
        }).addOnFailureListener(exception -> {
            callback.onResponse(new ElementResponse<>(new BaseException(exception.getMessage(), true)));
        });

    }


}
