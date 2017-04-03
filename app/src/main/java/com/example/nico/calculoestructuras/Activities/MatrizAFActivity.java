package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.GestorEstructura;
import com.example.nico.calculoestructuras.R;

public class MatrizAFActivity extends AppCompatActivity {
    GestorEstructura ge;
    String matriz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_af);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ge = new GestorEstructura();
        if(ge.getAlAF()!=null){
            matriz= ge.getAlAF();}

        TextView salida = (TextView) findViewById(R.id.sm);
        salida.setText(matriz);
        salida.setMovementMethod(new ScrollingMovementMethod());

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
       //     }
       // });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
