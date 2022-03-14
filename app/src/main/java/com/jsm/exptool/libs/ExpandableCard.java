package com.jsm.exptool.libs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jsm.exptool.R;


public class ExpandableCard extends LinearLayout {

    TextView titleTV, extraInfoTV;
    ImageButton collapseButton;
    LinearLayout innerContentLayout;
    boolean collapsed = true;
    String title = "", extraInfo = "";

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

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpandableCard,
                0, 0);

        try {
            collapsed = a.getBoolean(R.styleable.ExpandableCard_initial_expanded, true);
            title = a.getString(R.styleable.ExpandableCard_title);
            extraInfo = a.getString(R.styleable.ExpandableCard_extra_info);

        } finally {
            a.recycle();
        }

        inflate(context, R.layout.view_expandable_card, this);
        this.setOrientation(VERTICAL);
        titleTV = findViewById(R.id.titleTV);
        titleTV.setText(title != null ? title : "");
        extraInfoTV = findViewById(R.id.extraInfoTV);
        extraInfoTV.setText(extraInfo != null ? extraInfo : "");
        extraInfoTV.setVisibility(extraInfo != null ? VISIBLE: GONE);
        collapseButton = findViewById(R.id.collapseButton);
        innerContentLayout = findViewById(R.id.innerContentLayout);
        innerContentLayout.setVisibility(collapsed ? View.GONE : View.VISIBLE);
        collapseButton.setImageDrawable(context.getDrawable(collapsed? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_drop_up_24));
        collapseButton.setContentDescription(context.getString(collapsed? R.string.expandable_card_expand_content_description : R.string.expandable_card_contract_content_description) + titleTV.getText());
        collapseButton.setOnClickListener(view -> {
            collapsed = !collapsed;
            innerContentLayout.setVisibility(collapsed ? View.GONE : View.VISIBLE);
            collapseButton.setImageDrawable(context.getDrawable(collapsed? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_drop_up_24));

        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View [] views = new View [getChildCount()-1];
        //REcolocación de vistas dentro del card
        for (int i = 1; i<getChildCount(); i++) {
            views[i-1]= this.getChildAt(i);
        }
        for(int i = 0; i<views.length; i++){
            removeView(views[i]);
            innerContentLayout.addView(views[i]);
        }
    }
}
