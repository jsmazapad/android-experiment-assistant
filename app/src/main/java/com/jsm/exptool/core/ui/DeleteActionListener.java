package com.jsm.exptool.core.ui;

import android.content.Context;

public interface DeleteActionListener<T> {
    void delete(T element, Context context);
}
