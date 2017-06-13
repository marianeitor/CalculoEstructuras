package com.example.nico.calculoestructuras.Activities;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.Ejercicio;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

/**
 * Created by Carlos on May 2016.
 */

public class DialogGuardar extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_save, null))

                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText)getDialog().findViewById(R.id.edit_filename);
                        String titulo = editText.getText().toString();
                        if(titulo.equals(""))
                            editText.setText("Sin nombre");
                        final XmlParser xmlParser = new XmlParser(inflater.getContext());
                        EjercicioActual actual = (EjercicioActual)inflater.getContext();
                        actual.setNombreEjercicio(titulo);
                        xmlParser.guardarEjercicio(titulo);
                        Toast.makeText(getActivity(), "Guardado con éxito", Toast.LENGTH_SHORT).show();
                        //crearEjercicio(titulo, inflater.getContext());
                    }
                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogGuardar.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void crearEjercicio(final String titulo, final Context context) {
        final XmlParser xmlParser = new XmlParser(context);
        if (xmlParser.existe(titulo)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Ya existe un ejercicio con ese título");
            builder.setMessage("¿Desea reemplazarlo?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xmlParser.guardarEjercicio(titulo);
                    Toast.makeText(getActivity(), "Guardado con éxito", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "No se han guardado los cambios", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create();
            builder.show();
        } else {
            xmlParser.guardarEjercicio(titulo);
            Toast.makeText(getActivity(), "Guardado con éxito", Toast.LENGTH_SHORT).show();
        }
    }
}
