package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AgregarConectividadActivity extends AppCompatActivity {
    int nudoInicial = -1;
    int nudoFinal = -1;
    int numBarra = -1;
    Barra b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_conectividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.barra);
        CharSequence c = nb.getText();
        b =(Barra) getIntent().getSerializableExtra("barra");
        numBarra=b.getNumOrden();
        nb.setText(c + " " + numBarra + " : ");
        Spinner spinnerIn = (Spinner) findViewById(R.id.spi_nudos_conec_inic);
        Spinner spinnerFin = (Spinner) findViewById(R.id.spi_nudos_conec);
        ArrayList<Nudo> array = DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        ArrayList<String> arrayStrings = new ArrayList<>();
        for (int i = 0; i < array.size() ; i++)
        {
            arrayStrings.add("Nudo " + array.get(i).getnOrden() + "  (" + array.get(i).getX() +" ; " + array.get(i).getY() + ") ");
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerIn.setAdapter(adapter);
        spinnerFin.setAdapter(adapter);
        spinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String nudo=(String) parent.getSelectedItem();
                String[]s = nudo.split(" ");
                int i = Integer.parseInt(s[1]);
                nudoInicial=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(AgregarConectividadActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nudo=(String) parent.getSelectedItem();
                String[]s = nudo.split(" ");
                int i = Integer.parseInt(s[1]);
                nudoFinal=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(AgregarConectividadActivity.this,"Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void guardarConectividad(View view){
        if(nudoFinal == -1 || nudoInicial == -1 || numBarra == -1){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Conectividad conectividad = new Conectividad(numBarra,nudoInicial,nudoFinal);
            Intent in = new Intent();
            in.putExtra("conectividad", conectividad);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarConectividad(View view){ this.finish(); }

}
