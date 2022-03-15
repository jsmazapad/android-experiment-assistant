package com.jsm.exptool.binding;

import android.view.View;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.slider.Slider;

import java.util.List;

public class SliderBindings {
    @InverseBindingAdapter(attribute = "android:value")
    public static Float getValue(Slider slider) {
        return slider.getValue();
    }

    @BindingAdapter("android:valueAttrChanged")
    public static void setSlideListeners(Slider slider, InverseBindingListener attrChange) {
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            attrChange.onChange();
        });
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }








}
