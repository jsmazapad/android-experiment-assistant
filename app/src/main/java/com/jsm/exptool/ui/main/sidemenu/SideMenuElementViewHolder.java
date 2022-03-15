package com.jsm.exptool.ui.main.sidemenu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.ui.main.MenuElement;

class SideMenuElementViewHolder extends BaseRecyclerViewHolder<MenuElement> {

    private final TextView sideMenuTV;
    private final ImageView sideMenuIV;


    protected SideMenuElementViewHolder(View v) {
        super(v);
        sideMenuTV = v.findViewById(R.id.sideMenuTV);
        sideMenuIV = v.findViewById(R.id.sideMenuIV);
    }

    @Override
    public void fillViewHolder(MenuElement element) {
      
        sideMenuTV.setText(itemView.getContext().getString(element.getNameRes()).toUpperCase());
        sideMenuIV.setImageResource(element.getImageResourceSideMenu());
    }
}
