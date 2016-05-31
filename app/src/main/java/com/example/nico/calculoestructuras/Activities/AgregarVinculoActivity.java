package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AgregarVinculoActivity extends AppCompatActivity {
    Nudo n;
    Vinculo v;
    int numNudo;
    double restX = 0;
    double restY = 0;
    double restRot = 0;
    EditText valX;
    EditText valY;
    EditText valGiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vinculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        n = (Nudo) getIntent().getSerializableExtra("nudo");
        numNudo = n.getnOrden();
        nb.setText(c + " " + numNudo + " : ");
        Spinner spinnerRestX = (Spinner) findViewById(R.id.spi_rest_x);
        Spinner spinnerRestY = (Spinner) findViewById(R.id.spi_rest_y);
        Spinner spinnerRestRot = (Spinner) findViewById(R.id.spi_rest_rot);
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayStrings);
        spinnerRestX.setAdapter(adapter);
        spinnerRestY.setAdapter(adapter);
        spinnerRestRot.setAdapter(adapter);
        valX= (EditText) findViewById(R.id.editTextX);
        valY= (EditText) findViewById(R.id.editTextY);
        valGiro= (EditText) findViewById(R.id.editTextGiro);
        valX.setFocusable(false);
        valX.setEnabled(false);
        valY.setFocusable(false);
        valY.setEnabled(false);
        valGiro.setFocusable(false);
        valGiro.setEnabled(false);
        /* Esto es para que al volver a seleccionar un nodo que ya tiene restricciones
            los spinner tengan la configuracion correcta y no vuelvan todos a ponerse en "NO"
        if(n.isRestriccionX()){
            spinnerRestX.setSelection(1);
        }
        if(n.isRestriccionY()){
            spinnerRestY.setSelection(1);
        }
        if(n.isRestriccionGiro()){
            spinnerRestRot.setSelection(1);
        }
        */
        spinnerRestX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if (position == 0) {
                    valX.setText("");
                    valX.setEnabled(false);
                }
                if (position == 1) {
                    valX.setEnabled(true);
                    valX.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarVinculoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerRestY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if(position == 0){
                    valY.setText("");
                    valY.setEnabled(false);
                }
                if(position == 1){
                    valY.setEnabled(true);
                    valY.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarVinculoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerRestRot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if(position == 0){
                    valGiro.setText("");
                    valGiro.setEnabled(false);
                }
                if(position == 1){
                    valGiro.setEnabled(true);
                    valGiro.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarVinculoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarVinculo (View view)
    {
        /**/
        if(valX.isEnabled() && (valX.getText().toString().equals(""))||
                valY.isEnabled() && (valY.getText().toString().equals(""))||
                valGiro.isEnabled() && (valGiro.getText().toString().equals(""))){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if(valX.isEnabled())
                restX = Double.parseDouble(valX.getText().toString());
            if(valY.isEnabled())
                restY = Double.parseDouble(valY.getText().toString());
            if(valGiro.isEnabled())
                restRot = Double.parseDouble(valGiro.getText().toString());

        /*ContentValues values = new ContentValues();
        values.put("nudovinc",numNudo);
        values.put("restx", restX);
        values.put("resty", restY);
        values.put("restgiro", restRot);*/

            v = new Vinculo(numNudo);
            v.setRestX(restX);
            v.setRestY(restY);
            v.setRestGiro(restRot);

            //n.setRestricciones((restX != 0),(restY != 0),(restRot != 0));

            //Log.d("vinculo creada", v.toString());
            //DataBaseHelper.getDatabaseInstance(this).insertVinculo(values);
            Intent in = new Intent();
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("vinc", (Serializable) v);
        bundle.putSerializable("nudo",n);*/
            in.putExtra("vinc", v);
            //in.putExtras(bundle);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarVinculo(View view)
    {
        this.finish();
    }

}
