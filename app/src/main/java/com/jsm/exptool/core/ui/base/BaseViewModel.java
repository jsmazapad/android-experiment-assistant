package com.jsm.exptool.core.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.exceptions.BaseException;


/**
 * Clase base para ViewModels
 * Incluye mecanismo básico para mostrar errores
 */
public class BaseViewModel extends AndroidViewModel {

    protected MutableLiveData<BaseException> error = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BaseException> getError() {
        return error;
    }
}
