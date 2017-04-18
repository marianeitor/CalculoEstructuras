package com.example.nico.calculoestructuras.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nico.calculoestructuras.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NuevoEjercicioActivity extends AppCompatActivity {

    private EditText titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titulo = (EditText)findViewById(R.id.edit_titulo);
        Spinner spinnerLongitud = (Spinner)findViewById(R.id.spi_longitud);
        Spinner spinnerFuerza = (Spinner)findViewById(R.id.spi_fuerza);
        Spinner spinnerTemperatura = (Spinner)findViewById(R.id.spi_temperatura);

        ArrayList<String> arrayLong = new ArrayList<>();
        arrayLong.add("m (metros)" );
        arrayLong.add("cm (centímetros)");
        arrayLong.add("in (pulgadas)");
        arrayLong.add("ft (pies)");
        ArrayAdapter adapterL = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLong);
        spinnerLongitud.setAdapter(adapterL);

        ArrayList<String> arrayFuerza = new ArrayList<>();
        arrayFuerza.add("kg (kilogramos)" );
        arrayFuerza.add("t (toneladas)");
        arrayFuerza.add("lb (libras)");
        ArrayAdapter adapterF = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayFuerza);
        spinnerFuerza.setAdapter(adapterF);

        ArrayList<String> arrayTemp = new ArrayList<>();
        arrayTemp.add("°C (Centígrados)" );
        arrayTemp.add("°F (Fahrenheit)");
        ArrayAdapter adapterT = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayTemp);
        spinnerTemperatura.setAdapter(adapterT);
    }

    public void crearEjercicio(View view) {
        try {
            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            getBaseContext().openFileOutput(titulo.getText().toString() + ".xml",
                                    Context.MODE_PRIVATE));
            fout.close();
            Intent i = new Intent(NuevoEjercicioActivity.this, OpcionesMetodoActivity.class);
            startActivity(i);
            NuevoEjercicioActivity.this.finish();
            /* TODO: GUARDAR TAMBIEN LAS UNIDADES ELEGIDAS (CREAR TABLA EN BD) */

        } catch (IOException e) {
            Toast.makeText(NuevoEjercicioActivity.this, "Error al crear ejercicio", Toast.LENGTH_SHORT).show();
        }
    }

}
