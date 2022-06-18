package com.jsm.exptool.libs.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class ImageBindings {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }
}
