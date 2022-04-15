package com.jsm.exptool.core.ui.baserecycler;

import android.content.Context;

import androidx.navigation.NavController;

public interface OnRecyclerItemSelectedListener {
    /**
     * Define las acciones a realizar cuando se selecciona un elemento del listado
     *
     * @param position      posición del elemento dentro del listado
     * @param navController Controlador de navegación (para realizar navegación entre fragments )
     * @param c             Contexto donde se realiza la acción de selección
     */
    void onItemSelected(int position, NavController navController, Context c);
}
