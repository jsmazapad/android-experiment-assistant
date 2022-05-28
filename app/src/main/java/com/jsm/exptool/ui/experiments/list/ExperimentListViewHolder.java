package com.jsm.exptool.ui.experiments.list;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewHolder;
import com.jsm.exptool.core.utils.MemoryUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

public class ExperimentListViewHolder extends BaseRecyclerViewHolder<Experiment> {
    TextView titleTV, descriptionTV, dateTV, lengthTV, statusTV, sizeTV;
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
        sizeTV = v.findViewById(R.id.sizeTV);

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
        String dateText ="";
        if(element.getInitDate()!= null){
            dateText = DateProvider.dateToDisplayStringWithTime(element.getInitDate());
        }
        dateTV.setText(dateText);
        if("".equals(dateTV.getText())){
            dateTV.setVisibility(View.GONE);
        }else{
            dateTV.setVisibility(View.VISIBLE);
        }
        statusTV.setText(ExperimentProvider.getTranslatableStringFromExperimentStatus(element.getStatus(), itemView.getContext()));
        try {
            lengthTV.setText(TimeDisplayStringProvider.millisecondsToStringBestDisplay(element.getDuration()));
        }catch (Exception e){
            Log.e(this.getClass().getSimpleName(), e.getMessage(), e);
            lengthTV.setText("");
        }
        imagesIV.setVisibility(element.getConfiguration().isCameraEnabled()? View.VISIBLE: View.GONE);
        soundsIV.setVisibility(element.getConfiguration().isAudioEnabled()? View.VISIBLE: View.GONE);
        embeddingIV.setVisibility(element.getConfiguration().isCameraEnabled() && element.getConfiguration().isEmbeddingEnabled() ? View.VISIBLE: View.GONE);
        sensorsIV.setVisibility(element.getConfiguration().isSensorEnabled()? View.VISIBLE: View.GONE);

        sizeTV.setText(element.getSize());
        syncIV.setVisibility(element.isSyncPending()? View.VISIBLE: View.GONE);


    }
}
