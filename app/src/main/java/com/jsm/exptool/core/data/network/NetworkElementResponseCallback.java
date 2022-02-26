package com.jsm.exptool.core.data.network;

import com.jsm.exptool.core.data.repositories.responses.ElementResponse;

public interface NetworkElementResponseCallback<T>{
    void onResponse(ElementResponse<T> response);
}
