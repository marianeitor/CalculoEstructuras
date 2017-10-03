package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import com.example.nico.calculoestructuras.Negocio.PorticoPlano2;
import com.example.nico.calculoestructuras.R;

public class MatrizGlobalActivity extends AppCompatActivity {
    PorticoPlano2 porticoPlano;
    String matrizGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_global);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        porticoPlano = (PorticoPlano2) getIntent().getSerializableExtra("portico");
        matrizGlobal = porticoPlano.getMatrizToString(porticoPlano.getMatrizGlobal());
        TextView salida = (TextView) findViewById(R.id.matrizElemental);
        salida.setText(matrizGlobal);
        salida.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
