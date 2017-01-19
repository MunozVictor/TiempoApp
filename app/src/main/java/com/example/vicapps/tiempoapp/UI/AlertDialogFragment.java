package com.example.vicapps.tiempoapp.UI;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by 21542295 on 15/12/2016.
 */
public class AlertDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();//obtiene la actividad
        AlertDialog.Builder builder = new AlertDialog.Builder(context)//se crea el alert y mensajes
                .setTitle("Oops, sorry...")
                .setMessage("Try again")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
