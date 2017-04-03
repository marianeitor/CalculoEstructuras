package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterMatriz;
import com.example.nico.calculoestructuras.Negocio.GestorEstructura;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class MatricesElementalesActivity extends AppCompatActivity {
    GestorEstructura ge;
    ListView List;
    ListAdapterMatriz adapter;
    ArrayList<String> listaArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrices_elementales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ge = new GestorEstructura();
        List=(ListView) findViewById(R.id.lista_matrices);
        if(ge.getAl()!=null){
            listaArrays= ge.getAl();}
        else {
            listaArrays = new ArrayList<>();
        }
        adapter = new ListAdapterMatriz(this,listaArrays);
        List.setAdapter(adapter);

      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
       //     }
       // });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
