package com.jsm.exptool.data.network.deserializers;

import com.google.gson.JsonObject;

public interface DeserializerElementStrategyInterface<E> {

    E readItem(final JsonObject jsonObject);

}
