package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.PorticoPlano2;
import com.example.nico.calculoestructuras.R;

public class MatrizSFRActivity extends AppCompatActivity {
    PorticoPlano2 porticoPlano;
    String matrizSFF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_generico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        porticoPlano = (PorticoPlano2) getIntent().getSerializableExtra("portico");
        matrizSFF = porticoPlano.getMatrizToString(porticoPlano.getMatrizSFF());
        TextView salida = (TextView) findViewById(R.id.matriz);
        salida.setText(matrizSFF);
        salida.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
