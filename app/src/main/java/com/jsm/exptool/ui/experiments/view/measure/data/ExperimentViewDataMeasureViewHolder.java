package com.jsm.exptool.ui.experiments.view.measure.data;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
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
    ImageView syncIV, embeddingIV;

    protected ExperimentViewDataMeasureViewHolder(View v) {
        super(v);
        this.sensorTimeTV = v.findViewById(R.id.sensorTimeTV);
        this.sensorValueTV = v.findViewById(R.id.sensorValueTV);
        this.syncIV = v.findViewById(R.id.syncIV);
        this.embeddingIV = v.findViewById(R.id.embeddingIV);
    }

    @Override
    public void fillViewHolder(ExperimentRegister element) {
        sensorTimeTV.setText(DateProvider.dateToDisplayStringWithTime(element.getDate()));
        sensorValueTV.setVisibility(View.VISIBLE);
        if(element.isDataRemoteSynced()) {
            syncIV.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_baseline_check_24));
            syncIV.setContentDescription(itemView.getContext().getString(R.string.experiment_view_data_sync_check_content_description));
        }else{
            syncIV.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_baseline_close_24));
            syncIV.setContentDescription(itemView.getContext().getString(R.string.experiment_view_data_sync_pending_content_description));
        }
        if(element instanceof SensorRegister) {
            sensorValueTV.setText(((SensorRegister) element).getDisplayString());
            embeddingIV.setVisibility(View.GONE);
        }else if (element instanceof ImageRegister){
            ImageRegister imageElement = (ImageRegister) element;
            sensorValueTV.setText("");
            sensorValueTV.setVisibility(View.GONE);
            embeddingIV.setVisibility(View.VISIBLE);
            if(imageElement.getEmbedding() != null && !imageElement.getEmbedding().isEmpty()) {
                embeddingIV.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_baseline_check_24));
                embeddingIV.setContentDescription(itemView.getContext().getString(R.string.experiment_view_data_embedding_check_content_description));
            }else{
                embeddingIV.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_baseline_close_24));
                embeddingIV.setContentDescription(itemView.getContext().getString(R.string.experiment_view_data_embedding_pending_content_description));
            }

            if(!imageElement.isEmbeddingRemoteSynced()){
                syncIV.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_baseline_close_24));
                syncIV.setContentDescription(itemView.getContext().getString(R.string.experiment_view_data_sync_pending_content_description));
            }
        }else if (element instanceof AudioRegister){
            sensorValueTV.setText("");
            embeddingIV.setVisibility(View.GONE);
        }else if (element instanceof CommentRegister){
            //Comentarios
            sensorValueTV.setText(((CommentRegister)element).getComment());
            embeddingIV.setVisibility(View.GONE);
        }


    }
}
