package com.jsm.exptool.binding;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.slider.Slider;

public class CustomBindings {
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

}
