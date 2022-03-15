package com.jsm.exptool.binding;

import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class TextViewBindings {
    @BindingAdapter({"android:text"})
    public static void setText(TextView textView, Integer newSelectedValue) {
        if(newSelectedValue != null && !textView.getText().equals( String.valueOf(newSelectedValue)) )
        textView.setText(String.valueOf(newSelectedValue));
    }


}
