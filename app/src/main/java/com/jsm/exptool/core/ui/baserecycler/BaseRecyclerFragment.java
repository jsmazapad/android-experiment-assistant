package com.jsm.exptool.core.ui.baserecycler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseFragment;


/**
 * Clase base para la extender y crear de fragmentos que poseen un recyclerview
 * @param <BT> Databinding asociado al fragment
 * @param <VM> ViewModel asociado al fragment
 */
public abstract class BaseRecyclerFragment<BT extends ViewDataBinding, VM extends BaseRecyclerViewModel> extends BaseFragment<BT, VM> {


    private BaseRecyclerAdapter mAdapter;
    protected RecyclerView recyclerView;


    @Override
    protected abstract BT createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container);

    /**
     * Crea y devuelve el adapter que se usará en el recycler
     * @return  el adapter que se usará en el recycler
     */
    protected abstract BaseRecyclerAdapter createAdapter();
    @Override
    protected abstract VM createViewModel();

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
        super.executeExtraActionsInsideBindingInit();
        setupRecyclerView();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView =  super.onCreateView(inflater, container, savedInstanceState);
        LifecycleOwner lifeCycleOwner = this.getViewLifecycleOwner();
        Context context = this.getContext();
        viewModel.getApiResponseMediator().removeObservers(lifeCycleOwner);
        viewModel.getElements().removeObservers(lifeCycleOwner);
//        viewModel.getError().removeObservers(lifeCycleOwner);
        viewModel.getApiResponseMediator().observe(lifeCycleOwner, categoryApiListResponse -> Log.d("Activado", "Activado"));
        viewModel.getElements().observe(lifeCycleOwner, categoriesResponse -> {

                    recyclerView.getAdapter().notifyDataSetChanged();
                }

        );
//        viewModel.getError().observe(lifeCycleOwner, error -> {
//            viewModel.handleError((BaseException) error, context);
//        });

        return rootView;
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
