package com.jsm.exptool.core.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.jsm.exptool.BR;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.BaseUIErrorHandler;
import com.jsm.exptool.core.ui.RetryAction;
import com.jsm.exptool.core.ui.SetShowDialogFalse;
import com.jsm.exptool.core.ui.UIErrorHandler;
import com.jsm.exptool.core.libs.ModalMessage;


/**
 * Clase base para extender y crear fragmentos simples con arquitectura MVVM que incluyan Databinding entre ViewModel y Layout
 * Incluye mecanismo básico para mostrar errores
 * @param <BT> Data binding asociado a la vista
 * @param <VM> ViewModel asociado a este fragmento, extiende de la clase Base propia para ViewModels
 */
public abstract class BaseFragment<BT extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements RetryAction, SetShowDialogFalse {

    protected BT binding;
    protected VM viewModel;


    /**
     * Flag utilizado para asegurar en procesos complejos con programación reactiva que sólo se muestra un diálogo de error a la vez
     */
    private boolean showingDialog = false;

    /**
     * {@inheritdoc}
     * Incluye asociación del binding, el ViewModel y el fragmento
     * El binding sólo se asocia una vez para evitar problemas de duplicidades de instancias
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {
            viewModel = createViewModel();
            binding = createDataBinding(inflater, container);
            executeExtraActionsInsideBindingInit();
        }

        viewModel.initObservers(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Equivalente a binding.setViewModel(viewModel) genérico (viewModel es generado como variable)
        //Para que funcione, hay que tener en el proyecto al menos un layout con una variable viewModel y que se haya generado el código
        //Si da problemas, comentar, hacer un make project para que se genere el código y descomentar
        //Es necesario que estas dos líneas estén en onViewCreated para que al ir hacía adelante y despues volver no pierda el databinding)
        binding.setVariable(BR.viewModel, viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

    }

    /**
     * Ejecuta acciones extra cuando está inicializando el binding
     * Si no se anula se suscribe para mostrar errores con un modal de un sólo botón y sin acciones
     * NOTA: Este proceso no ocurre siempre que se llama a onCreateView
     */
    public void executeExtraActionsInsideBindingInit(){
        viewModel.getError().observe(getViewLifecycleOwner(), error ->{
            if(error != null)
            {
                if(!viewModel.isShowingDialog()) {
                    viewModel.setShowingDialog(true);
                    ModalMessage.showError(getContext(), error.getMessage(), null, ((dialog, which) -> viewModel.setShowDialogFalse()), null, null);
                }
            }
        });
    }

    /**
     * Devuelve una instancia del ViewModel asociado al Fragment para usarlo en onCreateView y vincularla al binding
     * @return ViewModel asociado al Fragment
     */
    protected abstract VM createViewModel();

    /**
     * Devuelve una instancia del DataBinding asociado al Fragment para usarlo en onCreateView y vincularla al ViewModel y el Fragment
     * @return DataBinding asociado al Fragment
     */
    protected abstract BT createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container);

    /**
     * Reintenta la operación tras un error
     */
    public void retryAction(){
        setShowDialogFalse();
    }


    private void setShowingDialog(boolean showingDialog) {
        this.showingDialog = showingDialog;
    }

    /**
     * Setea la opción de mostrar diálogo a false para controlar que no se muestre mas de un diálogo a la vez
     */
    public void setShowDialogFalse() {
        setShowingDialog(false);
    }

    /**
     * Devuelve el binding de la vista
     */
    protected BT getBinding() {
        return binding;
    }


}
