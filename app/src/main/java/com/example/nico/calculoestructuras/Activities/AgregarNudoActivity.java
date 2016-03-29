package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

public class AgregarNudoActivity extends AppCompatActivity {
    EditText x;
    EditText y;
    public static int EXTRA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        x = (EditText) findViewById(R.id.x_ingresada);
        y = (EditText) findViewById(R.id.y_ingresada);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void guardarNudo(View view)
    {
        String sx = x.getText().toString();
        String sy=y.getText().toString();
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
            ContentValues values = new ContentValues();
            values.put("coordx", n.getX());
            values.put("coordy", n.getY());
            DataBaseHelper.getDatabaseInstance(this).insertNudo(values);
            Intent in = new Intent();
            in.putExtra("nudo", n);
            setResult(RESULT_OK, in);
            this.finish();
        }
    }
    public void descartarNudo(View view)
    {
        this.finish();
    }
}
