package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Adapter.ListAdapterBarras;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AgregarBarraActivity extends AppCompatActivity {
    EditText elasticidad;
    EditText area;
    EditText inercia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_barra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        elasticidad = (EditText)findViewById(R.id.elasticidad_ingresada);
        area = (EditText)findViewById(R.id.area_ingresada);
        inercia = (EditText)findViewById(R.id.inercia_ingresada);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void guardarBarra(View view)
    {
        String s_elasticidad = elasticidad.getText().toString();
        String s_area = area.getText().toString();
        String s_inercia = inercia.getText().toString();
        if (s_elasticidad.matches("") || s_area.matches("") || s_inercia.matches(""))
        {
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Double double_elasticidad = Double.parseDouble(s_elasticidad);
            Double double_area = Double.parseDouble(s_area);
            Double double_inercia = Double.parseDouble(s_inercia);
            Barra b = new Barra(double_elasticidad, double_area, double_inercia);
            ContentValues values = new ContentValues();
            values.put("elasticidad", b.getElasticidad());
            values.put("area", b.getArea());
            values.put("inercia", b.getInercia());
            DataBaseHelper.getDatabaseInstance(this).insertBarra(values);
            Intent in = new Intent();
            in.putExtra("barra", b);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarBarra(View view){ this.finish();}

}
