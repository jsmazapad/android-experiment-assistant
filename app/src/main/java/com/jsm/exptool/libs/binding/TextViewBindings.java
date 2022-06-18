package com.jsm.exptool.libs.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TextViewBindings {
    @BindingAdapter({"android:text"})
    public static void setText(TextView textView, Integer newSelectedValue) {
        if(newSelectedValue != null && !textView.getText().equals( String.valueOf(newSelectedValue)) )
        textView.setText(String.valueOf(newSelectedValue));
    }


}
