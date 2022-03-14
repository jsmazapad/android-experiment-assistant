package com.jsm.exptool.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jsm.exptool.R;
import com.jsm.exptool.generated.callback.OnClickListener;

public class ExpandableCard extends LinearLayout {

    TextView titleTV;
    ImageButton collapseButton;
    LinearLayout innerContentLayout;
    boolean collapsed = true;

    public ExpandableCard(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableCard(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_expandable_card, this, true);
        titleTV = findViewById(R.id.titleTV);
        collapseButton = findViewById(R.id.collapseButton);
        innerContentLayout = findViewById(R.id.innerContentLayout);
        collapseButton.setImageDrawable(context.getDrawable(collapsed? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_drop_up_24));
        collapseButton.setOnClickListener(view -> {
            collapsed = !collapsed;
            innerContentLayout.setVisibility(collapsed ? View.GONE : View.VISIBLE);
            collapseButton.setImageDrawable(context.getDrawable(collapsed? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_drop_up_24));

        });
    }
}
