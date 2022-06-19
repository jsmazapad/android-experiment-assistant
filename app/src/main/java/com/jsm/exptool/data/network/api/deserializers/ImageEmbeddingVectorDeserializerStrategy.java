package com.jsm.exptool.data.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jsm.exptool.entities.embedding.ImageEmbeddingVector;


class ImageEmbeddingVectorDeserializerStrategy implements DeserializerElementStrategyInterface<ImageEmbeddingVector> {

    @Override
    public ImageEmbeddingVector readItem(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, ImageEmbeddingVector.class);
    }
}
