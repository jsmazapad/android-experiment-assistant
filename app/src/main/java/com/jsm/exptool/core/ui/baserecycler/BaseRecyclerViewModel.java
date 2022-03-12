package com.jsm.exptool.core.ui.baserecycler;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.BaseUIErrorHandler;
import com.jsm.exptool.core.ui.RetryAction;
import com.jsm.exptool.core.ui.SetShowDialogFalse;
import com.jsm.exptool.core.ui.UIErrorHandler;
import com.jsm.exptool.core.ui.loading.LoadingViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase base para ViewModel que incluye funcionalidades para mostrar un Recycler
 * @param <T> Tipo de objeto del listado usado para alimentar los datos del recycler
 * @param <ResponseType> Tipo de datos que se obtiene del response del repositorio
 */
public abstract class BaseRecyclerViewModel<T, ResponseType> extends LoadingViewModel  {

    protected MutableLiveData<List<T>> elements = new MutableLiveData<>();
    protected MutableLiveData<BaseException> error = new MutableLiveData<>();
    /**
     * Mediador LiveData, obtiene los datos del livedata, los procesa
     * y aporta conexión para triggearlo desde el ViewModel pero observarlo desde el fragment, es necesario para que todos los
     * livedata empleados estén encadenados al ciclo de vida del fragment
     */
    protected MediatorLiveData<ListResponse<ResponseType>> apiResponseMediator = new MediatorLiveData<>();
    /**
     * Livedata empleado para obtener los datos del repositorio de forma reactiva
     */
    protected MutableLiveData<ListResponse<ResponseType>> apiResponseRepositoryHolder = new MutableLiveData<>();
    /**
     * flag reactivo para indicar cuando se tiene que mostrar un texto indicando que no hay elementos
     */
    protected MutableLiveData<Boolean> textEmptyVisibility = new MutableLiveData<>();
    /**
     * flag reactivo para indicar cuando se tiene que ocultar el recyclerview
     */
    protected MutableLiveData<Boolean> recyclerVisibility = new MutableLiveData<>();



    public BaseRecyclerViewModel(Application app, Object... args) {
        super(app);
        setConstructorParameters(args);
        getRepositoryData();

        elements.setValue(new ArrayList<>());
        textEmptyVisibility.setValue(false);
        recyclerVisibility.setValue(true);

    }

    /**
     * Obtiene los datos del repositorio
     * y setea el valor reactivo que correponda (los elementos del listado en caso de éxito o el error en caso de fallo)
     */
    protected void getRepositoryData() {
        isLoading.setValue(true);
        apiResponseMediator.removeSource(apiResponseRepositoryHolder);
        apiResponseMediator.addSource(apiResponseRepositoryHolder, apiListResponse -> {
            isLoading.setValue(false);
            if (apiListResponse.getError() == null) {
                BaseRecyclerViewModel.this.elements.setValue(transformResponse(apiListResponse));
                textEmptyVisibility.postValue(apiListResponse.getResultList() == null || apiListResponse.getResultList().size() == 0);
                recyclerVisibility.setValue(true);
            } else {
                if (!isShowingDialog()) {
                    setShowingDialog(true);
                    recyclerVisibility.setValue(false);
                    textEmptyVisibility.setValue(true);
                    BaseRecyclerViewModel.this.error.postValue(apiListResponse.getError());
                }
            }
        });

        callRepositoryForData();

    }

    /**
     * Obtiene los elementos del listado de manera reactiva
     * @return  un livedata con los elementos del listado de datos que se usa para alimaentar los datos del recyclerview
     */
    public LiveData<List<T>> getElements() {
        return elements;
    }

    /**
     * Obtiene el mediador Livedata
     * @return un livedata para poder vincular la operación de actualización de datos con el fragment
     */
    public LiveData<ListResponse<ResponseType>> getApiResponseMediator() {
        return apiResponseMediator;
    }

    public LiveData<BaseException> getError() {
        return error;
    }

    public void setError(BaseException error) {
        this.error.setValue(error);
    }


    public LiveData<Boolean> getTextEmptyVisibility() {
        return textEmptyVisibility;
    }


    /**
     * Acción para refrescar los datos del repositorio, por defecto si no se anula
     * llama para obtener los datos del repositorio nuevamente
     */
    public void onRefresh() {
        getRepositoryData();
    }



    /**
     * Función para realizar transformaciones entre el listado obtenido en la respuesta y
     * el listado que se utliza en el ViewModel
     * @param response
     * @return
     */
    public abstract List<T> transformResponse(ListResponse<ResponseType> response);

    /**
     * Define las acciones a realizar cuando se selecciona un elemento del listado
     * @param position posición del elemento dentro del listado
     * @param navController Controlador de navegación (para realizar navegación entre fragments )
     * @param c Contexto donde se realiza la acción de selección
     */
    public abstract void onItemSelected(int position, NavController navController, Context c);

    /**
     * Setea de manera dinámica parámetros adicionales en el constructor
     * @param args valores de parámetros adicionales
     */
    public abstract void setConstructorParameters(Object... args);

    /**
     * Ejecuta la llamada al repositorio para traerse los datos
     */
    public abstract void callRepositoryForData();

    /**
     * Reintenta la operación de traer datos del repositorio
     */
    @Override
    public void retryAction(){
        setShowDialogFalse();
        onRefresh();
    }

}
