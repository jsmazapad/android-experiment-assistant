package com.jsm.exptool.core.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

import com.jsm.exptool.R;


/**
 * Clase de utilidad para generar Dialogs (Ventanas modales)
 */
public class ModalMessage {

    /**
     * Crea un diálogo de dos botones, si no se le pasa buttonOkLabel pone la etiqueta por defecto del botón Ok, si no se le pasa buttonCancelLabel
     * no genera el botón de cancelar
     *
     * @param context
     * @param title
     * @param message
     * @param buttonOkLabel
     * @param buttonOkListener
     * @param buttonCancelLabel
     * @param buttonCancelListener
     *
     */
    public static void showModalMessage(Context context, String title, String message, @Nullable String buttonOkLabel, @Nullable DialogInterface.OnClickListener buttonOkListener, @Nullable String buttonCancelLabel, @Nullable DialogInterface.OnClickListener buttonCancelListener) {
        showModalMessageWithThreeButtons(context, title, message, buttonOkLabel, buttonOkListener, buttonCancelLabel, buttonCancelListener, null, null);
    }


    /**
     * Crea un diálogo de tres botones, si no se le pasa buttonOkLabel pone la etiqueta por defecto del botón Ok, si no se le pasa buttonCancelLabel
     * no genera el botón de cancelar, si no se le pasa buttonMiddleLabel no genera el botón de enmedio
     * @param context
     * @param title
     * @param message
     * @param buttonOkLabel
     * @param buttonOkListener
     * @param buttonCancelLabel
     * @param buttonCancelListener
     * @param buttonMiddleLabel
     * @param buttonMiddleListener
     */
    public static void showModalMessageWithThreeButtons(Context context, String title, String message, @Nullable String buttonOkLabel, @Nullable DialogInterface.OnClickListener buttonOkListener, @Nullable String buttonCancelLabel, @Nullable DialogInterface.OnClickListener buttonCancelListener, @Nullable String buttonMiddleLabel, @Nullable DialogInterface.OnClickListener buttonMiddleListener) {

        String okLabel = context.getString(R.string.default_modal_okButton);
        if (buttonOkLabel != null) {
            okLabel = buttonOkLabel;
        }

        //Descomentar para customizar título
//        TextView textView = new TextView(context);
//        textView.setPadding(80,20,80,20);
//        textView.setTextSize(21.0f);
//        textView.setText(title);
        //Título custom para que pueda ocupar mas de dos líneas
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                //.setCustomTitle(textView)
                .setTitle(title)
                .setMessage(message).setPositiveButton(okLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(buttonOkListener != null) {
                    buttonOkListener.onClick(dialog, which);

                }

            }
        });
        if (buttonCancelLabel != null || buttonCancelListener != null) {
            if (buttonCancelLabel == null) {
                buttonCancelLabel = context.getString(R.string.default_modal_cancelButton);
            }

            dialogBuilder.setNegativeButton(buttonCancelLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(buttonCancelListener != null) {
                            buttonCancelListener.onClick(dialog, which);

                        }

                    }
                });


        }
        if (buttonMiddleLabel != null) {


            dialogBuilder.setNeutralButton(buttonMiddleLabel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(buttonMiddleListener != null) {
                        buttonMiddleListener.onClick(dialog, which);

                    }

                }
            });


        }

        dialogBuilder.setCancelable(false);


        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * Crea un diálogo de error, si no se le pasa buttonOkLabel pone la etiqueta por defecto del botón Ok, si no se le pasa buttonCancelLabel
     * no genera el botón de cancelar
     * @param context
     * @param message
     * @param buttonOkLabel
     * @param buttonOkListener
     * @param buttonCancelLabel
     * @param buttonCancelListener
     */
    public static void showError(Context context, String message, @Nullable String buttonOkLabel, @Nullable DialogInterface.OnClickListener buttonOkListener, @Nullable String buttonCancelLabel, @Nullable DialogInterface.OnClickListener buttonCancelListener) {
        ModalMessage.showModalMessage(context, "Error", message, buttonOkLabel, buttonOkListener, buttonCancelLabel, buttonCancelListener);
    }

    public static void showSuccessfulOperation(Context context, @Nullable DialogInterface.OnClickListener buttonOkListener){
        ModalMessage.showModalMessage(context, context.getString(R.string.default_info_title),
                context.getString(R.string.default_succesful_operation),null, buttonOkListener, null, null);
    }

    public static void showFailureOperation(Context context, @Nullable DialogInterface.OnClickListener buttonOkListener){
        ModalMessage.showError(context,
                context.getString(R.string.default_failure_operation),null, buttonOkListener, null, null);
    }
}
