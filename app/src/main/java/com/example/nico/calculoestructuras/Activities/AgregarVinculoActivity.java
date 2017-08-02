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

import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

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

        final SwitchCompat switchX = (SwitchCompat) findViewById(R.id.switch_rest_x);
        final SwitchCompat switchY = (SwitchCompat) findViewById(R.id.switch_rest_y);
        final SwitchCompat switchZ = (SwitchCompat) findViewById(R.id.switch_rest_z);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        Vinculo vinc = (Vinculo) getIntent().getSerializableExtra("vinculo");
        n = (Nudo) getIntent().getSerializableExtra("nudo");
        numNudo = n.getnOrden();
        nb.setText(c + " " + numNudo + " : ");

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
            los switches tengan la configuracion correcta y no vuelvan todos a ponerse en "NO" */
        if(!Double.isNaN(vinc.getRestX())){
            switchX.setChecked(true);
            valX.setText(Double.toString(vinc.getRestX()));
            valX.setEnabled(true);
        }
        if(!Double.isNaN(vinc.getRestY())){
            switchY.setChecked(true);
            valY.setText(Double.toString(vinc.getRestY()));
            valY.setEnabled(true);
        }
        if(!Double.isNaN(vinc.getRestGiro())){
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_save_as:
//                android.app.FragmentManager fragmentManager = getFragmentManager();
//                DialogGuardar dialogGuardar = new DialogGuardar();
//                dialogGuardar.show(fragmentManager, "tag");
//                return true;
            case R.id.action_save:
                String titulo = ((EjercicioActual)getApplicationContext()).getNombreEjercicio();
                final XmlParser xmlParser = new XmlParser(getApplicationContext());
                xmlParser.guardarEjercicio(titulo);
                Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void guardarVinculo (View view)
    {
        if(valX.isEnabled() && (valX.getText().toString().equals(""))||
                valY.isEnabled() && (valY.getText().toString().equals(""))||
                valGiro.isEnabled() && (valGiro.getText().toString().equals(""))){
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
        } else {
            restX = valX.isEnabled() ? Double.parseDouble(valX.getText().toString()) : Double.NaN;
            restY = (valY.isEnabled()) ? Double.parseDouble(valY.getText().toString()) : Double.NaN;
            restRot = (valGiro.isEnabled()) ? Double.parseDouble(valGiro.getText().toString()) : Double.NaN;

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
