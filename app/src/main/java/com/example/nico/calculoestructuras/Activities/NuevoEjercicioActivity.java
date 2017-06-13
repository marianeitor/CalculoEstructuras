package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.Ejercicio;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

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
        EjercicioActual actual = (EjercicioActual)getApplication();
        final XmlParser xmlParser = new XmlParser(getApplicationContext());
        String _titulo = titulo.getText().toString();
        actual.setNombreEjercicio(_titulo);
        xmlParser.guardarEjercicio(_titulo);
        Intent i = new Intent(NuevoEjercicioActivity.this, OpcionesMetodoActivity.class);
        startActivity(i);
        NuevoEjercicioActivity.this.finish();
            /* TODO: GUARDAR TAMBIEN LAS UNIDADES ELEGIDAS (CREAR TABLA EN BD) */
    }

}
