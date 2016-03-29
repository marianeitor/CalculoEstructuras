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

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AgregarCargaEnBarraActivity extends AppCompatActivity {
    int nudoInicial;
    int nudoFinal;
    int numBarra;
    Barra b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carga_en_barra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.barra);
        CharSequence c = nb.getText();
        //numBarra = getIntent().getIntExtra("numero",0);
        b =(Barra) getIntent().getSerializableExtra("barra");
        numBarra=b.getNumOrden();
        nb.setText(c + " " + numBarra + " : ");
        Spinner spinnerIn = (Spinner) findViewById(R.id.spi_nudos_conec_inic);
        Spinner spinnerFin = (Spinner) findViewById(R.id.spi_nudos_conec);
        ArrayList<Nudo> array = DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        final EditText cargaDis= (EditText) findViewById(R.id.editTextDist);
        //  final EditText cargaX= (EditText) findViewById(R.id.editTextCX);
        // final EditText cargaY= (EditText) findViewById(R.id.editTextCY);
        // final EditText cargaZ= (EditText) findViewById(R.id.editTextCZ);
        cargaDis.setEnabled(false);
        cargaDis.setFocusable(false);
        // cargaX.setEnabled(false);
        // cargaX.setFocusable(false);
        // cargaY.setEnabled(false);
        // cargaY.setFocusable(false);
        // cargaZ.setEnabled(false);
        //cargaZ.setEnabled(false);

        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO");
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerIn.setAdapter(adapter);
        spinnerFin.setAdapter(adapter);
        spinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String rest = (String) parent.getSelectedItem();
                if (rest.equals("NO")) {
                    cargaDis.setText("");
                    cargaDis.setEnabled(false);
                }
                if (rest.equals("SI")) {
                    cargaDis.setEnabled(true);
                    cargaDis.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(AgregarCargaEnBarraActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });
        spinnerFin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if (rest.equals("NO")) {
                    //cargaX.setText("");
                    //cargaX.setEnabled(false);
                    //cargaY.setText("");
                    //cargaY.setEnabled(false);
                    //cargaZ.setText("");
                    //cargaZ.setEnabled(false);
                }
                if (rest.equals("SI")) {
                    //cargaX.setEnabled(true);
                    //cargaX.setFocusableInTouchMode(true);
                    //cargaY.setEnabled(true);
                    //cargaY.setFocusableInTouchMode(true);
                    //cargaZ.setEnabled(true);
                    //cargaZ.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(AgregarCargaEnBarraActivity.this,"Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
