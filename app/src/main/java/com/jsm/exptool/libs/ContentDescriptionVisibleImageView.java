package com.jsm.exptool.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

public class ContentDescriptionVisibleImageView extends androidx.appcompat.widget.AppCompatImageView{

    OnClickListener showContentDescriptionClickListener = v -> {
        Snackbar.make(v, v.getContentDescription(), Snackbar.LENGTH_LONG).show();
    };

    public ContentDescriptionVisibleImageView(@NonNull Context context) {
        super(context);
        this.setOnClickListener(this.showContentDescriptionClickListener);
    }

    public ContentDescriptionVisibleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this.showContentDescriptionClickListener);
    }

    public ContentDescriptionVisibleImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(this.showContentDescriptionClickListener);
    }


}
