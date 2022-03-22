package com.jsm.exptool.ui.experiments.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

public class ExperimentListViewHolder extends BaseRecyclerViewHolder<Experiment> {
    TextView titleTV, descriptionTV, dateTV, lengthTV, statusTV;
    ImageView imagesIV, soundsIV, embeddingIV, sensorsIV, syncIV;

    protected ExperimentListViewHolder(View v) {
        super(v);
        titleTV = v.findViewById(R.id.titleTV);
        descriptionTV = v.findViewById(R.id.descriptionTV);
        dateTV = v.findViewById(R.id.dateTV);
        lengthTV = v.findViewById(R.id.lengthTV);
        imagesIV = v.findViewById(R.id.imagesIV);
        soundsIV = v.findViewById(R.id.soundsIV);
        embeddingIV = v.findViewById(R.id.embeddingIV);
        sensorsIV = v.findViewById(R.id.sensorsIV);
        syncIV = v.findViewById(R.id.syncIV);
        statusTV = v.findViewById(R.id.statusTV);

    }

    @Override
    public void fillViewHolder(Experiment element) {
        titleTV.setText(element.getTitle());
        descriptionTV.setText(element.getDescription());
        if("".equals(descriptionTV.getText())){
            descriptionTV.setVisibility(View.GONE);
        }else{
            descriptionTV.setVisibility(View.VISIBLE);
        }
        dateTV.setText(DateProvider.dateToDisplayStringWithTime(element.getInitDate()));
        if("".equals(dateTV.getText())){
            dateTV.setVisibility(View.GONE);
        }else{
            dateTV.setVisibility(View.VISIBLE);
        }
        statusTV.setText(ExperimentProvider.getTranslatableStringFromExperimentStatus(element.getStatus(), itemView.getContext()));
        try {
            lengthTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(element.getEndDate().getTime() - element.getInitDate().getTime()));
        }catch (Exception e){
            e.printStackTrace();
            lengthTV.setText("");
        }
        imagesIV.setVisibility(element.getConfiguration().isCameraEnabled()? View.VISIBLE: View.GONE);
        soundsIV.setVisibility(element.getConfiguration().isAudioEnabled()? View.VISIBLE: View.GONE);
        embeddingIV.setVisibility(element.getConfiguration().isCameraEnabled() && element.getConfiguration().isEmbeddingEnabled() ? View.VISIBLE: View.GONE);
        sensorsIV.setVisibility(element.getConfiguration().isSensorEnabled()? View.VISIBLE: View.GONE);

        //syncIV.setVisibility(element.getConfiguration().isCameraEnabled()? View.VISIBLE: View.GONE);


    }
}
