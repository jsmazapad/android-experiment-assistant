package com.jsm.exptool.data.network.api.deserializers;

import com.google.gson.JsonArray;

import java.util.List;

public interface DeserializerListStrategyInterface<E> {

    List<E> readItems(final JsonArray jsonArray);

}
