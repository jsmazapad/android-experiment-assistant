package com.jsm.exptool.core.data.network.exceptions;


import com.jsm.exptool.core.exceptions.BaseException;

/**
 * Excepción para errores de sesión
 */
public class InvalidSessionException extends BaseException {
    public InvalidSessionException(String generalSessionError) {
        super(generalSessionError, false);
    }
}
