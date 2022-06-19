package com.jsm.exptool.data.network.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jsm.exptool.core.data.network.CustomDeserializerProvider;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.api.deserializers.ElementDeserializer;

public class AppDeserializerProvider implements CustomDeserializerProvider {

    public GsonBuilder registerCustomDeserializers(GsonBuilder gsonBuilder) {

        gsonBuilder.registerTypeAdapter(NetworkElementResponse.class, new ElementDeserializer<JsonObject>());
        return gsonBuilder;
    }

}
