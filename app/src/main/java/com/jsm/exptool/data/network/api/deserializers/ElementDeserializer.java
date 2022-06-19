package com.jsm.exptool.data.network.api.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.embedding.ImageEmbeddingVector;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class ElementDeserializer<E> implements JsonDeserializer<NetworkElementResponse<E>> {

    private DeserializerElementStrategyInterface deserializerElementStrategy;

    @Override
    public NetworkElementResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        System.out.println(" \n Comienza deserializer \n");
        System.out.println(json.toString());

        JsonObject contentObject = json.getAsJsonObject();

        if (((ParameterizedType) typeOfT).getActualTypeArguments()[0] == ImageEmbeddingVector.class) {
            deserializerElementStrategy = new ImageEmbeddingVectorDeserializerStrategy();
        } else if (((ParameterizedType) typeOfT).getActualTypeArguments()[0] == RemoteSyncResponse.class) {
            deserializerElementStrategy = new RemoteSyncResponseDeserializerStrategy();
        }else if (((ParameterizedType) typeOfT).getActualTypeArguments()[0] == LoginResponse.class) {
            deserializerElementStrategy = new LoginResponseDeserializerStrategy();
        } else {
            throw new JsonParseException("Ha habido un error con la estructura de la respuesta");
        }

        return new NetworkElementResponse<>(deserializerElementStrategy.readItem(contentObject));
    }
}
