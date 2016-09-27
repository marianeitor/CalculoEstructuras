package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    int nroBarra = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_barra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent i = getIntent();
        Barra b = (Barra)getIntent().getSerializableExtra("barra");
        elasticidad = (EditText)findViewById(R.id.elasticidad_ingresada);
        area = (EditText)findViewById(R.id.area_ingresada);
        inercia = (EditText)findViewById(R.id.inercia_ingresada);
        if(b != null){
            elasticidad.setText(Double.toString(b.getElasticidad()));
            area.setText(Double.toString(b.getArea()));
            inercia.setText(Double.toString(b.getInercia()));
            nroBarra = (int)getIntent().getSerializableExtra("nroBarra");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_save){
            Toast toast = Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //Snackbar.make(getCurrentFocus(), "Guardado", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
            Barra barra = new Barra(double_elasticidad, double_area, double_inercia);
            Intent in = new Intent();
            in.putExtra("barra", barra);
            if(nroBarra != -1){
                in.putExtra("nroBarra", nroBarra);
            }
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarBarra(View view){ this.finish();}

}
