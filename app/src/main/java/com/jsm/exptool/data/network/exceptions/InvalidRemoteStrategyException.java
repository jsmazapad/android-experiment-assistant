package com.jsm.exptool.data.network.exceptions;

import static com.jsm.exptool.config.NetworkConstants.INVALID_REMOTE_SYNC_METHOD;

import com.jsm.exptool.core.exceptions.BaseException;

public class InvalidRemoteStrategyException extends BaseException {
    public InvalidRemoteStrategyException(String message) {
        super(message, false);
    }

    public InvalidRemoteStrategyException() {
        super(INVALID_REMOTE_SYNC_METHOD, false);
    }
}
