package com.jsm.exptool.core.data.network.exceptions;


import com.jsm.exptool.core.exceptions.BaseException;

/**
 * Excepción para errores internos de backend
 */
public class InternalBackendException extends BaseException {

    public InternalBackendException(String message) {
        super(message, false);
    }
}