package com.jsm.exptool.data.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;


class LoginResponseDeserializerStrategy implements DeserializerElementStrategyInterface<LoginResponse> {

    @Override
    public LoginResponse readItem(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, LoginResponse.class);
    }
}
