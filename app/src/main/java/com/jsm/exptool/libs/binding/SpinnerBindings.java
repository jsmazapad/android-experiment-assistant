package com.jsm.exptool.libs.binding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class SpinnerBindings {

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
        if (newSelectedValue != null && !spinner.getSelectedItem().equals(newSelectedValue)) {
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
