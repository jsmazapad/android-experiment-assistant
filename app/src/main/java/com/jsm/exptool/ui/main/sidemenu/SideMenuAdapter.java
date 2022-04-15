package com.jsm.exptool.ui.main.sidemenu;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.ui.main.MainActivity;
import com.jsm.exptool.ui.main.MenuElement;



public class SideMenuAdapter extends BaseRecyclerAdapter<MenuElement, SideMenuElementViewHolder> {

    private final Context context;

    public SideMenuAdapter(Context context, BaseRecyclerViewModelListener viewModel, LifecycleOwner lifeCycleOwner, NavController navController, int listItemResource) {
        super(context, viewModel, viewModel.getElements(), navController, listItemResource);
        this.context = context;
    }

    @Override
    public SideMenuElementViewHolder instanceViewHolder(View v) {
        return new SideMenuElementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SideMenuElementViewHolder holder, int position) {
        MenuElement element = elements.getValue().get(position);
        holder.fillViewHolder(element);
        holder.itemView.setOnClickListener(v -> {

            onRecyclerItemSelectedListener.onItemSelected(position, navController, v.getContext());
            if (context instanceof MainActivity) {
                ((MainActivity) context).getDrawerLayout().closeDrawers();
            }

        });

    }
}
