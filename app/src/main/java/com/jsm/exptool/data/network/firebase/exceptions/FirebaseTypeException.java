package com.jsm.exptool.data.network.firebase.exceptions;

import static com.jsm.exptool.config.NetworkConstants.FIREBASE_BAD_TYPE;

import com.jsm.exptool.core.exceptions.BaseException;

public class FirebaseTypeException extends BaseException {
    public FirebaseTypeException(String message) {
        super(message, false);
    }
    public FirebaseTypeException() {
        super(FIREBASE_BAD_TYPE, false);
    }
}
