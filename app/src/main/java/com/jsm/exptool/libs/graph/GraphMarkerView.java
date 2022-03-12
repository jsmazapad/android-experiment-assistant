package com.jsm.exptool.libs.graph;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.jsm.exptool.R;

public class GraphMarkerView extends MarkerView {

    private TextView tvContent;

    public GraphMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.measure);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String string = "";
        switch (highlight.getDataSetIndex()){
            case 0:
                string += "X: ";
                tvContent.setBackgroundColor(getResources().getColor(R.color.purple_200));
                break;
            case 1:
                string += "Y: ";
                tvContent.setBackgroundColor(getResources().getColor(R.color.purple_700));
                break;
            case 2:
                string += "Z: ";
                tvContent.setBackgroundColor(getResources().getColor(R.color.purple_500));
                break;

        }
        tvContent.setText(string + e.getY());

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }}
