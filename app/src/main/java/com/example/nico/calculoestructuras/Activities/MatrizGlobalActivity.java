package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.GestorEstructura;
import com.example.nico.calculoestructuras.Negocio.GestorEstructura2;
import com.example.nico.calculoestructuras.R;

public class MatrizGlobalActivity extends AppCompatActivity {
    GestorEstructura2 gestorEstructura;
    String matrizGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_global);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gestorEstructura = new GestorEstructura2();
//        if(gestorEstructura.getGlobal()!=null){
//            matrizGlobal= gestorEstructura.getGlobal();}
        matrizGlobal = gestorEstructura.getGlobal();
        TextView salida = (TextView) findViewById(R.id.sm);
        salida.setText(matrizGlobal);
        salida.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
