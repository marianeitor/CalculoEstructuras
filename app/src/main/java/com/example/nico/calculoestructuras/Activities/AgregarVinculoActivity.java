package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

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
    /* Se cambiaron por switches
    Spinner spinnerRestX;
    Spinner spinnerRestY;
    Spinner spinnerRestRot;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vinculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwitchCompat switchX = (SwitchCompat) findViewById(R.id.switch_rest_x);
        final SwitchCompat switchY = (SwitchCompat) findViewById(R.id.switch_rest_y);
        final SwitchCompat switchZ = (SwitchCompat) findViewById(R.id.switch_rest_z);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        Vinculo vinc = (Vinculo) getIntent().getSerializableExtra("vinculo");
        n = (Nudo) getIntent().getSerializableExtra("nudo");
        numNudo = n.getnOrden();
        nb.setText(c + " " + numNudo + " : ");
        /*
        spinnerRestX = (Spinner) findViewById(R.id.spi_rest_x);
        spinnerRestY = (Spinner) findViewById(R.id.spi_rest_y);
        spinnerRestRot = (Spinner) findViewById(R.id.spi_rest_rot);
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayStrings);
        spinnerRestX.setAdapter(adapter);
        spinnerRestY.setAdapter(adapter);
        spinnerRestRot.setAdapter(adapter); */

        valX = (EditText) findViewById(R.id.editTextX);
        valY = (EditText) findViewById(R.id.editTextY);
        valGiro = (EditText) findViewById(R.id.editTextGiro);
        valX.setFocusable(false);
        valX.setEnabled(false);
        valY.setFocusable(false);
        valY.setEnabled(false);
        valGiro.setFocusable(false);
        valGiro.setEnabled(false);

        /* Esto es para que al volver a seleccionar un nodo que ya tiene restricciones
            los spinner tengan la configuracion correcta y no vuelvan todos a ponerse en "NO" */
        if(n.isRestriccionX()){
            switchX.setChecked(true);
            valX.setText(Double.toString(vinc.getRestX()));
            valX.setEnabled(true);
        }
        if(n.isRestriccionY()){
            switchY.setChecked(true);
            valY.setText(Double.toString(vinc.getRestY()));
            valY.setEnabled(true);
        }
        if(n.isRestriccionGiro()){
            switchZ.setChecked(true);
            valGiro.setText(Double.toString(vinc.getRestGiro()));
            valGiro.setEnabled(true);
        }

        switchX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    valX.setEnabled(true);
                    valX.setFocusableInTouchMode(true);
                } else {
                    valX.setText("");
                    valX.setEnabled(false);
                }
            }
        });

        switchY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    valY.setEnabled(true);
                    valY.setFocusableInTouchMode(true);
                } else {
                    valY.setText("");
                    valY.setEnabled(false);
                }
            }
        });

        switchZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    valGiro.setEnabled(true);
                    valGiro.setFocusableInTouchMode(true);
                } else {
                    valGiro.setText("");
                    valGiro.setEnabled(false);
                }
            }
        });

        /*
        spinnerRestX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String rest = (String) parent.getSelectedItem();
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
                //String rest = (String) parent.getSelectedItem();
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
                //String rest = (String) parent.getSelectedItem();
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
        */
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
        if(valX.isEnabled() && (valX.getText().toString().equals(""))||
                valY.isEnabled() && (valY.getText().toString().equals(""))||
                valGiro.isEnabled() && (valGiro.getText().toString().equals(""))){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
        } else {
            if(valX.isEnabled())
                restX = Double.parseDouble(valX.getText().toString());
            if(valY.isEnabled())
                restY = Double.parseDouble(valY.getText().toString());
            if(valGiro.isEnabled())
                restRot = Double.parseDouble(valGiro.getText().toString());

            v = new Vinculo(numNudo);
            v.setRestX(restX);
            v.setRestY(restY);
            v.setRestGiro(restRot);

            Intent in = new Intent();
            in.putExtra("vinc", v);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarVinculo(View view)
    {
        this.finish();
    }

}
