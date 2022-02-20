package com.jsm.exptool.ui.experiments.create.configure;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.slider.Slider;
import com.jsm.exptool.R;
import com.jsm.exptool.model.MySensor;

public class SelectFrequencyDialog {
    public static void createDialog(Context context, MySensor sensor, Handler.Callback listener, final float minValue, final float maxValue, final float defaultValue){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_delay, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        mBuilder.setTitle(context.getString(sensor.getRName()));

        final TextView minDelayTV = mView.findViewById(R.id.minDelayTV);
        TextView maxDelayTV = mView.findViewById(R.id.maxDelayTV);
        final TextView delay = mView.findViewById(R.id.delayTV);
        ImageButton minusButton = mView.findViewById(R.id.minusButton);
        ImageButton plusButton = mView.findViewById(R.id.plusButton);

        final Slider slider =  mView.findViewById(R.id.sliderFrequency);
        slider.setValueFrom(minValue);
        slider.setValueTo(maxValue);

        minDelayTV.setText(minValue +  " ms");
        maxDelayTV.setText(maxValue +  " ms");
        delay.setText(String.valueOf(sensor.getInterval()));





        mBuilder.setPositiveButton(R.string.default_modal_okButton, (dialog, which) -> sensor.setInterval((int) slider.getValue()));
        mBuilder.setNegativeButton(R.string.default_modal_cancelButton, (dialog, which) -> dialog.cancel());

        minusButton.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v)  {
                if (delayValue[0] > mySensor.getIntervalMin()){
                    delayValue[0] = delayValue[0] - 100;
                    seekBar.setProgress(delayValue[0]);
                    delay.setText(String.valueOf(delayValue[0]));
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v)  {
                if (delayValue[0] < MyConstant.MAX_INTERVAL_MILLIS){
                    delayValue[0] = delayValue[0] + 100;
                    seekBar.setProgress(delayValue[0]);
                    delay.setText(String.valueOf(delayValue[0]));
                }
            }
        });

        AlertDialog alertDialogAndroid = mBuilder.create();
        alertDialogAndroid.show();
    }
}
