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

import java.util.ArrayList;

public class AgregarVinculoActivity extends AppCompatActivity {
    Nudo n;
    int numNudo;
    double restX;
    double restY;
    double restRot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vinculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nb = (TextView) findViewById(R.id.nudo);
        CharSequence c = nb.getText();
        n =(Nudo) getIntent().getSerializableExtra("nudo");
        numNudo=n.getnOrden();
        nb.setText(c + " " + numNudo + " : ");
        Spinner spinnerRestX = (Spinner) findViewById(R.id.spi_rest_x);
        Spinner spinnerRestY = (Spinner) findViewById(R.id.spi_rest_y);
        Spinner spinnerRestRot = (Spinner) findViewById(R.id.spi_rest_rot);
        ArrayList<String> arrayStrings = new ArrayList<>();
        arrayStrings.add("NO" );
        arrayStrings.add("SI");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayStrings);
        spinnerRestX.setAdapter(adapter);
        spinnerRestY.setAdapter(adapter);
        spinnerRestRot.setAdapter(adapter);
        final EditText valX= (EditText) findViewById(R.id.editTextX);
        final EditText valY= (EditText) findViewById(R.id.editTextY);
        final EditText valGiro= (EditText) findViewById(R.id.editTextGiro);
        valX.setFocusable(false);
        valX.setEnabled(false);
        valY.setFocusable(false);
        valY.setEnabled(false);
        valGiro.setFocusable(false);
        valGiro.setEnabled(false);
        spinnerRestX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rest = (String) parent.getSelectedItem();
                if (rest.equals("NO")) {
                    restX = 0;
                    valX.setText("");
                    valX.setEnabled(false);
                }
                if (rest.equals("SI")) {
                    valX.setEnabled(true);
                    valX.setFocusableInTouchMode(true);
                    String text=valX.toString();
                    restX=new Double(text).doubleValue();
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
                if(rest.equals("NO")){
                    restY= 0;
                    valY.setText("");
                    valY.setEnabled(false);
                }if(rest.equals("SI")){
                    valY.setEnabled(true);
                    valY.setFocusableInTouchMode(true);
                    String text=valY.toString();
                    restY=new Double(text).doubleValue();
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
                if(rest.equals("NO")){
                    restRot= 0;
                    valGiro.setText("");
                    valGiro.setEnabled(false);
                }if(rest.equals("SI")){
                    valGiro.setEnabled(true);
                    valGiro.setFocusableInTouchMode(true);
                    String text=valGiro.toString();
                    restRot=new Double(text).doubleValue();
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
        ContentValues values = new ContentValues();
        values.put("nudovinc",numNudo);
        values.put("restx", restX);
        values.put("resty", restY);
        values.put("restgiro", restRot);

        Vinculo v = new Vinculo(numNudo);
        if(restX!=0){
            v.setRestX(restX);
        }
        if(restY!=0){
            v.setRestY(restY);
        }
        if(restRot!=0){
            v.setRestGiro(restRot);
        }

        Log.d("vinculo creada", v.toString());
        DataBaseHelper.getDatabaseInstance(this).insertVinculo(values);
        Intent in = new Intent();
        in.putExtra("nudo", n);
        setResult(RESULT_OK, in);
        this.finish();
    }

    public void descartarVinculo(View view)
    {
        this.finish();
    }

}
