package com.jsm.exptool.core.ui.baserecycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsm.exptool.BR;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;


/**
 * Clase base para la extender y crear de fragmentos que poseen un recyclerview
 * @param <BT> Databinding asociado al fragment
 * @param <VM> ViewModel asociado al fragment
 */
public abstract class BaseRecyclerFragment<BT extends ViewDataBinding, VM extends BaseRecyclerViewModel> extends BaseFragment {

    protected VM viewModel;
    private BaseRecyclerAdapter mAdapter;
    protected RecyclerView recyclerView;
    protected BT binding;

    @Override
    protected abstract BT getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container);

    /**
     * Crea y devuelve el adapter que se usará en el recycler
     * @return  el adapter que se usará en el recycler
     */
    protected abstract BaseRecyclerAdapter createAdapter();
    @Override
    protected abstract VM getViewModel();

    /**
     * Sirve para setear en el adapter el recurso layout que se usará para las vistas de los items del recycler
     * @return el recurso layout que se usará para las vistas de los items del recycler
     */
    protected abstract int getListItemResourceId();

    /**
     * {@inheritdoc}
     * Configura el recyclerView durante el proceso de inicialización del binding
     */
    @Override
    public void executeExtraActionsInsideBindingInit(){
        setupRecyclerView();
    }

    //TODO Ver porque no se puede heredar este método del padre
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {
            viewModel = getViewModel();
            binding = getDataBinding(inflater, container);
            //Equivalente a binding.setViewModel(viewModel) genérico (viewModel es generado como variable)
            binding.setVariable(BR.viewModel, viewModel);

            binding.setLifecycleOwner(this);

            setupRecyclerView();

        }

        return binding.getRoot();
    }

    /**
     * Configura el recyclerView
     */
    protected void setupRecyclerView() {
        recyclerView = binding.getRoot().findViewById(R.id.elementsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        if (mAdapter == null) {
            mAdapter = createAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
