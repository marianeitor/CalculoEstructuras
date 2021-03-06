package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

public class AgregarNudoActivity extends AppCompatActivity {
    EditText x;
    EditText y;
    int nroNudo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent i = getIntent();
        Nudo n = (Nudo)getIntent().getSerializableExtra("nudo");
        x = (EditText) findViewById(R.id.x_ingresada);
        y = (EditText) findViewById(R.id.y_ingresada);
        if(n != null){
            x.setText(Double.toString(n.getX()));
            y.setText(Double.toString(n.getY()));
            nroNudo = (int)getIntent().getSerializableExtra("nroNudo");
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

    public void guardarNudo(View view)
    {
        String sx = x.getText().toString();
        String sy = y.getText().toString();
        if (sx.matches("") || sy.matches(""))
        {
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Double double_x = Double.parseDouble(sx);
            Double double_y = Double.parseDouble(sy);
            Nudo n = new Nudo(double_x, double_y);

            Intent in = new Intent();
            in.putExtra("nudo", n);
            if(nroNudo != -1){
                in.putExtra("nroNudo",nroNudo);
            }
            setResult(RESULT_OK, in);
            this.finish();
        }
    }
    public void descartarNudo(View view)
    {
        this.finish();
    }
}
