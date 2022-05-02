package com.jsm.exptool.ui.experiments.view.measure.data;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.providers.DateProvider;

public class ExperimentViewDataMeasureViewHolder extends BaseRecyclerViewHolder<ExperimentRegister> {

    TextView sensorTimeTV, sensorValueTV;
    CheckBox syncCheckBox, embeddingCheckBox;

    protected ExperimentViewDataMeasureViewHolder(View v) {
        super(v);
        this.sensorTimeTV = v.findViewById(R.id.sensorTimeTV);
        this.sensorValueTV = v.findViewById(R.id.sensorValueTV);
        this.syncCheckBox = v.findViewById(R.id.syncCheckBox);
        this.embeddingCheckBox = v.findViewById(R.id.embeddingCheckBox);
    }

    @Override
    public void fillViewHolder(ExperimentRegister element) {
        sensorTimeTV.setText(DateProvider.dateToDisplayStringWithTime(element.getDate()));
        syncCheckBox.setChecked(element.isDataRemoteSynced());
        if(element instanceof SensorRegister) {
            sensorValueTV.setText(((SensorRegister) element).getDisplayString());
            embeddingCheckBox.setVisibility(View.GONE);
        }else if (element instanceof ImageRegister){
            ImageRegister imageElement = (ImageRegister) element;
            sensorValueTV.setText("");
            embeddingCheckBox.setVisibility(View.VISIBLE);
            embeddingCheckBox.setChecked(imageElement.getEmbedding() != null && !imageElement.getEmbedding().isEmpty());
        }else if (element instanceof AudioRegister){
            sensorValueTV.setText("");
            embeddingCheckBox.setVisibility(View.GONE);
        }else if (element instanceof CommentRegister){
            //Comentarios
            sensorValueTV.setText(((CommentRegister)element).getComment());
            embeddingCheckBox.setVisibility(View.GONE);
        }


    }
}
