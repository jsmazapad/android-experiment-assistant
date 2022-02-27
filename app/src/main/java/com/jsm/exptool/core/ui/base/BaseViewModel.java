package com.jsm.exptool.core.ui.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.BaseUIErrorHandler;
import com.jsm.exptool.core.ui.RetryAction;
import com.jsm.exptool.core.ui.SetShowDialogFalse;
import com.jsm.exptool.core.ui.UIErrorHandler;


/**
 * Clase base para ViewModels
 * Incluye mecanismo básico para mostrar errores
 */
public abstract class BaseViewModel extends AndroidViewModel implements  SetShowDialogFalse, RetryAction {

    protected MutableLiveData<BaseException> error = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BaseException> getError() {
        return error;
    }

    /**
     *     Manejador de errores, sirve para realizar las acciones adecuadas a cada uno de ellos
     */
    protected UIErrorHandler errorHandler = new BaseUIErrorHandler();

    /**
     * Flag utilizado para asegurar en procesos complejos con programación reactiva que sólo se muestra un diálogo de error a la vez
     */
    private boolean showingDialog = false;

    /**
     * Flag para indicar si se está mostrando actualmente un dialog
     * @return
     */
    protected boolean isShowingDialog() {
        return showingDialog;
    }

    protected void setShowingDialog(boolean showingDialog) {
        this.showingDialog = showingDialog;
    }

    /**
     * Setea la opción de mostrar diálogo a false para controlar que no se muestre mas de un diálogo a la vez
     */
    public void setShowDialogFalse() {
        setShowingDialog(false);
    }

    /**
     * Maneja los errores
     * @param exception Excepción con la información del error
     * @param context Contexto donde se procesará el error
     */
    public void handleError(BaseException exception, Context context){
        errorHandler.handleError(exception, context, this, this);
    }

    /**
     * Reintenta la operación de trar datos del repositorio
     */
    public void retryAction(){
        setShowDialogFalse();
    }

}
