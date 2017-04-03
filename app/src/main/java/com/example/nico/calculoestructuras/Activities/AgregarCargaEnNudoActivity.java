package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

public class AgregarCargaEnNudoActivity extends AppCompatActivity {
    Nudo n;
    CargaEnNudo carga;
    int numNudo;
    double cargaX = 0;
    double cargaY = 0;
    double cargaZ = 0;
    EditText valX;
    EditText valY;
    EditText valZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carga_en_nudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwitchCompat switchX = (SwitchCompat)findViewById(R.id.switch_carga_x);
        final SwitchCompat switchY = (SwitchCompat)findViewById(R.id.switch_carga_y);
        final SwitchCompat switchZ = (SwitchCompat)findViewById(R.id.switch_carga_z);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        n =(Nudo) getIntent().getSerializableExtra("nudo");
        CargaEnNudo cargaNudo = (CargaEnNudo) getIntent().getSerializableExtra("carga");
        numNudo=n.getnOrden();
        nb.setText(c + " " + numNudo + " : ");

        /* Se reemplazaron los spinners por switches
        Spinner spinnerCargaX = (Spinner) findViewById(R.id.spi_carga_x);
        Spinner spinnerCargaY = (Spinner) findViewById(R.id.spi_carga_y);
        Spinner spinnerCargaZ = (Spinner) findViewById(R.id.spi_carga_z);


        // Inicializa los spinner
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerCargaX.setAdapter(adapter);
        spinnerCargaY.setAdapter(adapter);
        spinnerCargaZ.setAdapter(adapter);
        */

        // Inicializa los editText como deshabilitados
        valX = (EditText) findViewById(R.id.editTextX);
        valY = (EditText) findViewById(R.id.editTextY);
        valZ = (EditText) findViewById(R.id.editTextGiro);
        valX.setFocusable(false);
        valX.setEnabled(false);
        valY.setFocusable(false);
        valY.setEnabled(false);
        valZ.setFocusable(false);
        valZ.setEnabled(false);

        /* Esto es para que al volver a seleccionar una carga existente
        los spinner tengan la configuracion correcta y no vuelvan todos a ponerse en "NO" */

        if(cargaNudo != null){
            if(cargaNudo.getCargaEnX() != 0){
                switchX.setChecked(true);
                valX.setText(Double.toString(cargaNudo.getCargaEnX()));
                valX.setEnabled(true);
            }
            if(cargaNudo.getCargaEnY() != 0){
                switchY.setChecked(true);
                valY.setText(Double.toString(cargaNudo.getCargaEnY()));
                valY.setEnabled(true);
            }
            if(cargaNudo.getCargaEnZ() != 0){
                switchZ.setChecked(true);
                valZ.setText(Double.toString(cargaNudo.getCargaEnZ()));
                valZ.setEnabled(true);
            }
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
                    valZ.setEnabled(true);
                    valZ.setFocusableInTouchMode(true);
                } else {
                    valZ.setText("");
                    valZ.setEnabled(false);
                }
            }
        });


        /*
        spinnerCargaX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerCargaY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String rest = (String) parent.getSelectedItem();
                if (position == 0) {
                    valY.setText("");
                    valY.setEnabled(false);
                }
                if (position == 1) {
                    valY.setEnabled(true);
                    valY.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerCargaZ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String rest = (String) parent.getSelectedItem();
                if (position == 0) {
                    valZ.setText("");
                    valZ.setEnabled(false);
                }
                if (position == 1) {
                    valZ.setEnabled(true);
                    valZ.setFocusableInTouchMode(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AgregarCargaEnNudoActivity.this, "Debe seleccionar algun valor", Toast.LENGTH_SHORT).show();
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void guardarCarga(View view){

        if(valX.isEnabled() && (valX.getText().toString().equals(""))||
                valY.isEnabled() && (valY.getText().toString().equals(""))||
                valZ.isEnabled() && (valZ.getText().toString().equals(""))){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
        } else {
            if(valX.isEnabled())
                cargaX = Double.parseDouble(valX.getText().toString());
            if(valY.isEnabled())
                cargaY = Double.parseDouble(valY.getText().toString());
            if(valZ.isEnabled())
                cargaZ = Double.parseDouble(valZ.getText().toString());

        /*ContentValues values = new ContentValues();
        values.put("nudovinc",numNudo);
        values.put("restx", restX);
        values.put("resty", restY);
        values.put("restgiro", restRot);*/

            carga = new CargaEnNudo(numNudo);
            carga.setCargaEnX(cargaX);
            carga.setCargaEnY(cargaY);
            carga.setCargaEnZ(cargaZ);

            //n.setRestricciones((restX != 0),(restY != 0),(restRot != 0));

            //Log.d("vinculo creada", v.toString());
            //DataBaseHelper.getDatabaseInstance(this).insertVinculo(values);
            Intent in = new Intent();
        /*Bundle bundle = new Bundle();
        bundle.putSerializable("vinc", (Serializable) v);
        bundle.putSerializable("nudo",n);*/
            in.putExtra("carga", carga);
            //in.putExtras(bundle);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }

    public void descartarCarga(View view){ this.finish();}

}
