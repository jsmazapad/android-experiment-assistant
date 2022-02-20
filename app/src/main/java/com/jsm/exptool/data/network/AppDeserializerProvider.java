package com.jsm.exptool.data.network;

import com.google.gson.GsonBuilder;
import com.jsm.exptool.core.data.network.CustomDeserializerProvider;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.deserializers.ElementDeserializer;
import com.jsm.exptool.model.ImageEmbeddingVector;

public class AppDeserializerProvider implements CustomDeserializerProvider {

    public GsonBuilder registerCustomDeserializers(GsonBuilder gsonBuilder) {

        gsonBuilder.registerTypeAdapter(NetworkElementResponse.class, new ElementDeserializer<ImageEmbeddingVector>());

        return gsonBuilder;
    }

}
