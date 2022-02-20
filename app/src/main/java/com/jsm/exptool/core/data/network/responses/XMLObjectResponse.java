package com.jsm.exptool.core.data.network.responses;

import java.util.List;

public interface XMLObjectResponse <E> {
    List<E> getItems();
}
