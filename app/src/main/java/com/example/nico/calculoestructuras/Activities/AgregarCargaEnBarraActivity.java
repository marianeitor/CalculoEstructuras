package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AgregarCargaEnBarraActivity extends AppCompatActivity {
    double cargaX;
    double cargaY;
    double cargaZ;
    double cargaDist;
    double cargaPuntDistanciaXY;
    double cargaPuntDistanciaZ;
    int numBarra;

    Barra b;
    CargaEnBarra cargaEnBarra;
    Spinner spinnerCargaDist;
    Spinner spinnerCargaPuntual;
    Spinner spinnerCargaPuntualZ;
    EditText editCargaDist;
    EditText editCargaX;
    EditText editCargaY;
    EditText editCargaZ;
    EditText editDistanciaXY;
    EditText editDistanciaZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carga_en_barra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.barra);
        CharSequence c = nb.getText();
        b =(Barra) getIntent().getSerializableExtra("barra");
        cargaEnBarra = (CargaEnBarra) getIntent().getSerializableExtra("carga");
        numBarra=b.getNumOrden();
        nb.setText(c + " " + numBarra + " : ");

        spinnerCargaDist = (Spinner) findViewById(R.id.spi_carga_dist);
        spinnerCargaPuntual = (Spinner) findViewById(R.id.spi_carga_puntual);
        spinnerCargaPuntualZ = (Spinner) findViewById(R.id.spi_carga_puntual_z);
        editCargaDist = (EditText) findViewById(R.id.edit_carga_dist);
        editCargaX = (EditText) findViewById(R.id.edit_carga_x);
        editCargaY = (EditText) findViewById(R.id.edit_carga_y);
        editCargaZ = (EditText) findViewById(R.id.edit_carga_z);
        editDistanciaXY = (EditText) findViewById(R.id.edit_distancia_xy);
        editDistanciaZ = (EditText) findViewById(R.id.edit_distancia_z);
        // Inicializa los spinner
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerCargaDist.setAdapter(adapter);
        spinnerCargaPuntual.setAdapter(adapter);
        spinnerCargaPuntualZ.setAdapter(adapter);

        // Inicializa los editText como deshabilitados
        editCargaDist.setFocusable(false);
        editCargaDist.setEnabled(false);
        editCargaX.setFocusable(false);
        editCargaX.setEnabled(false);
        editCargaY.setFocusable(false);
        editCargaY.setEnabled(false);
        editCargaZ.setFocusable(false);
        editCargaZ.setEnabled(false);
        editDistanciaXY.setFocusable(false);
        editDistanciaXY.setEnabled(false);
        editDistanciaZ.setFocusable(false);
        editDistanciaZ.setEnabled(false);

        /* Esto es para que al volver a seleccionar una carga existente
        los spinner tengan la configuracion correcta y no vuelvan todos a ponerse en "NO" */
        if(cargaEnBarra != null){
            if(cargaEnBarra.getCargaDistribuida() != 0){
                spinnerCargaDist.setSelection(1);
                editCargaDist.setText(Double.toString(cargaEnBarra.getCargaDistribuida()));
            }
            if(cargaEnBarra.getCargaPuntualEnX() != 0){
                spinnerCargaPuntual.setSelection(1);
                editCargaX.setText(Double.toString(cargaEnBarra.getCargaPuntualEnX()));
            }
            if(cargaEnBarra.getCargaPuntualEnY() != 0){
                spinnerCargaPuntual.setSelection(1);
                editCargaY.setText(Double.toString(cargaEnBarra.getCargaPuntualEnY()));
            }
            if(cargaEnBarra.getCargaPuntualEnZ() != 0){
                spinnerCargaPuntualZ.setSelection(1);
                editCargaZ.setText(Double.toString(cargaEnBarra.getCargaPuntualEnZ()));
            }
            if(cargaEnBarra.getCargaPuntualDistXY() != 0){
                spinnerCargaPuntual.setSelection(1);
                editDistanciaXY.setText(Double.toString(cargaEnBarra.getCargaPuntualDistXY()));
            }
            if(cargaEnBarra.getCargaPuntualDistZ() != 0){
                spinnerCargaPuntualZ.setSelection(1);
                editDistanciaZ.setText(Double.toString(cargaEnBarra.getCargaPuntualDistZ()));
            }
        }

        spinnerCargaDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0) {
                    editCargaDist.setText("");
                    editCargaDist.setEnabled(false);
                }
                if (position == 1) {
                    editCargaDist.setEnabled(true);
                    editCargaDist.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(AgregarCargaEnBarraActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerCargaPuntual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    editCargaX.setText("");
                    editCargaX.setEnabled(false);
                    editCargaY.setText("");
                    editCargaY.setEnabled(false);
                    editDistanciaXY.setText("");
                    editDistanciaXY.setEnabled(false);
                }
                if (position == 1) {
                    editCargaX.setEnabled(true);
                    editCargaX.setFocusableInTouchMode(true);
                    editCargaY.setEnabled(true);
                    editCargaY.setFocusableInTouchMode(true);
                    editDistanciaXY.setEnabled(true);
                    editDistanciaXY.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(AgregarCargaEnBarraActivity.this,"Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerCargaPuntualZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    editCargaZ.setText("");
                    editCargaZ.setEnabled(false);
                    editDistanciaZ.setText("");
                    editDistanciaZ.setEnabled(false);
                }
                if (position == 1) {
                    editCargaZ.setEnabled(true);
                    editCargaZ.setFocusableInTouchMode(true);
                    editDistanciaZ.setEnabled(true);
                    editDistanciaZ.setFocusableInTouchMode(true);
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

    public void guardarCarga(View view){
        if(editCargaDist.isEnabled() && (editCargaDist.getText().toString().equals(""))||
                editCargaX.isEnabled() && (editCargaX.getText().toString().equals(""))||
                editCargaY.isEnabled() && (editCargaY.getText().toString().equals(""))||
                editCargaZ.isEnabled() && (editCargaZ.getText().toString().equals("")) ||
                editDistanciaXY.isEnabled() && (editDistanciaXY.getText().toString().equals(""))||
                editDistanciaZ.isEnabled() && (editDistanciaZ.getText().toString().equals(""))){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if(editCargaDist.isEnabled())
                cargaDist = Double.parseDouble(editCargaDist.getText().toString());
            if(editCargaX.isEnabled())
                cargaX = Double.parseDouble(editCargaX.getText().toString());
            if(editCargaY.isEnabled())
                cargaY = Double.parseDouble(editCargaY.getText().toString());
            if(editCargaZ.isEnabled())
                cargaZ = Double.parseDouble(editCargaZ.getText().toString());
            if(editDistanciaXY.isEnabled())
                cargaPuntDistanciaXY = Double.parseDouble(editDistanciaXY.getText().toString());
            if(editDistanciaZ.isEnabled())
                cargaPuntDistanciaZ = Double.parseDouble(editDistanciaZ.getText().toString());

            CargaEnBarra cargaEnBarra = new CargaEnBarra(numBarra);
            cargaEnBarra.setCargaPuntualEnX(cargaX);
            cargaEnBarra.setCargaPuntualEnY(cargaY);
            cargaEnBarra.setCargaPuntualEnZ(cargaZ);
            cargaEnBarra.setCargaDistribuida(cargaDist);
            cargaEnBarra.setCargaPuntualDistXY(cargaPuntDistanciaXY);
            cargaEnBarra.setCargaPuntualDistZ(cargaPuntDistanciaZ);

            Intent in = new Intent();
            in.putExtra("carga", cargaEnBarra);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

}
