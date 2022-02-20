package com.jsm.exptool.core.ui.baserecycler;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Clase base para extender y usar como ViewHolder en el adapter
 * @param <T> Tipo del objeto que será utilizado para rellenar los parámetros de la vista
 */
public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    // each data item is just a string in this case

    protected BaseRecyclerViewHolder(View v) {
        super(v);
    }


    /**
     * Rellena los valores de la vista en base al elemento que se le pasa
     * @param element
     */
    public abstract void fillViewHolder(T element);
}


