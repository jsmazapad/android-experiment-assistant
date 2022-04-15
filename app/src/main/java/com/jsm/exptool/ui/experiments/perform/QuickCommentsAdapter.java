package com.jsm.exptool.ui.experiments.perform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.OnRecyclerItemSelectedListener;

import java.util.List;

public class QuickCommentsAdapter extends BaseRecyclerAdapter<String, QuickCommentsViewHolder> {

    /**
     * @param context
     * @param onRecyclerItemSelectedListener callback para cuando se hace click en un elemento
     * @param elements                       Listado de elementos del recycler
     * @param navController                  Controlador de navegación (Android jetpack)
     * @param listItemResource               Recurso layout donde se incluye la vista de cada item del recycler
     */
    public QuickCommentsAdapter(Context context, OnRecyclerItemSelectedListener onRecyclerItemSelectedListener, LiveData<List<String>> elements, NavController navController, int listItemResource) {
        super(context, onRecyclerItemSelectedListener, elements, navController, listItemResource);
    }

    @Override
    public QuickCommentsViewHolder instanceViewHolder(View v) {
        return new QuickCommentsViewHolder(v);
    }
}
