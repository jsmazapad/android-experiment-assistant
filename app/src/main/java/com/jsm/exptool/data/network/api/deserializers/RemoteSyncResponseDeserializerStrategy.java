package com.jsm.exptool.data.network.api.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;


class RemoteSyncResponseDeserializerStrategy implements DeserializerElementStrategyInterface<RemoteSyncResponse> {

    @Override
    public RemoteSyncResponse readItem(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, RemoteSyncResponse.class);
    }
}
