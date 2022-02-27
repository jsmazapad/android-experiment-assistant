package com.jsm.exptool.binding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
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

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }



    /*
    SPINNER
     */

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner spinner, Object newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<Object>) spinner.getAdapter()).getPosition(newSelectedValue);
            spinner.setSelection(pos, true);
        }
    }
    
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Object captureSelectedValue(AppCompatSpinner spinner) {
        return spinner.getSelectedItem();
    }

    @BindingAdapter({"spinnerAdapter"})
    public static <T> void setSpinnerAdapter(AppCompatSpinner spinner, ArrayAdapter<T> adapter){
        spinner.setAdapter(adapter);
    }

}
