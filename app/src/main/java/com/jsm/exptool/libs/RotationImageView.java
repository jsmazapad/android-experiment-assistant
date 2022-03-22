package com.jsm.exptool.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class RotationImageView extends androidx.appcompat.widget.AppCompatImageView {
    public RotationImageView(Context context) {
        super(context);
    }

    public RotationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        this.startAnimation(rotate);
    }


}
