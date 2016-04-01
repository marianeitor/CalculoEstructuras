package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AgregarCargaEnNudoActivity extends AppCompatActivity {
    Nudo n;
    int numNudo;
    boolean restX;
    boolean restY;
    boolean restRot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carga_en_nudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        n =(Nudo) getIntent().getSerializableExtra("nudo");
        numNudo=n.getnOrden();
        //numNudo=Integer.parseInt(getIntent().getStringExtra("numero"));
        nb.setText(c + " " + numNudo + " : ");
        Spinner spinnerCargaX = (Spinner) findViewById(R.id.spi_carga_x);
        Spinner spinnerCargaY = (Spinner) findViewById(R.id.spi_carga_y);
        Spinner spinnerCargaZ = (Spinner) findViewById(R.id.spi_carga_z);
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerCargaX.setAdapter(adapter);
        spinnerCargaY.setAdapter(adapter);
        spinnerCargaZ.setAdapter(adapter);
        final EditText valX= (EditText) findViewById(R.id.editTextX);
        final EditText valY= (EditText) findViewById(R.id.editTextY);
        final EditText valGiro= (EditText) findViewById(R.id.editTextGiro);
        valX.setFocusable(false);
        valX.setEnabled(false);
        valY.setFocusable(false);
        valY.setEnabled(false);
        valGiro.setFocusable(false);
        valGiro.setEnabled(false);
        spinnerCargaX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if (rest.equals("NO")) {
                    restX = false;
                    valX.setText("");
                    valX.setEnabled(false);
                }
                if (rest.equals("SI")) {
                    restX = true;
                    valX.setEnabled(true);
                    valX.setFocusableInTouchMode(true);
                }
                //Toast.makeText(VinculosActivity.this,"lala " + i, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerCargaY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if(rest.equals("NO")){
                    restY= false;
                    valY.setText("");
                    valY.setEnabled(false);
                }if(rest.equals("SI")){
                    restY=true;
                    valY.setEnabled(true);
                    valY.setFocusableInTouchMode(true);
                }
                // Toast.makeText(VinculosActivity.this,"lala " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerCargaZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if(rest.equals("NO")){
                    restRot= false;
                    valGiro.setText("");
                    valGiro.setEnabled(false);
                }if(rest.equals("SI")){
                    restRot=true;
                    valGiro.setEnabled(true);
                    valGiro.setFocusableInTouchMode(true);
                }
                // Toast.makeText(VinculosActivity.this,"lala " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
