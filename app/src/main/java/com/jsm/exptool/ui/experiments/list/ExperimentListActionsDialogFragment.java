package com.jsm.exptool.ui.experiments.list;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ExperimentListActionsDialogFragment extends DialogFragment {
    Context mContext;

    public ExperimentListActionsDialogFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setTitle("Set Wallpaper?");
        alertDialogBuilder.setMessage("Are you sure?");
        // null should be your on click listener
        alertDialogBuilder.setPositiveButton("OK", null);
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }

    public static ExperimentListActionsDialogFragment newInstance() {
        ExperimentListActionsDialogFragment f = new ExperimentListActionsDialogFragment();
        return f;
    }

}

