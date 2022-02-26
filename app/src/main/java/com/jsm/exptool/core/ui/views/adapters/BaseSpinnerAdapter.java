package com.jsm.exptool.core.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;

import java.util.List;

public abstract class BaseSpinnerAdapter<T, VH extends BaseRecyclerViewHolder<T>> extends ArrayAdapter<T> {

    int listItemResource;
    Context context;

    public BaseSpinnerAdapter(Context context, List<T> elements, int listItemResource) {
        super(context, listItemResource, elements);
        this.context = context;
        this.listItemResource = listItemResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getRowView(convertView, position, parent);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getRowView(convertView, position, parent);
    }

    private View getRowView(View convertView, int position, ViewGroup parent) {

        T element = getItem(position);

        VH holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater =  LayoutInflater.from(context);
            rowView = inflater.inflate(listItemResource, parent, false);
            holder = instanceViewHolder(rowView);
            holder.fillViewHolder(element);
            rowView.setTag(holder);
        }



        return rowView;
    }


    public abstract VH instanceViewHolder(View v);

}
