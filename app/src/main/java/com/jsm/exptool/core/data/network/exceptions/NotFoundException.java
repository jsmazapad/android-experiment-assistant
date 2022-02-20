package com.jsm.exptool.core.data.network.exceptions;


import com.jsm.exptool.core.exceptions.BaseException;

/**
 * Excepción para recursos no encontrados (código 404)
 */
public class NotFoundException extends BaseException {
    public NotFoundException(String notFoundError) {
        super(notFoundError, false);
    }
}
