package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterOpc;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class AnalizarResultadosActivity extends AppCompatActivity {
    ArrayList<String> listaOpciones;
    ListView list;
    ListAdapterOpc adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_resultados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listaopc);
        if(list == null)
        {
            ListView listaCreada = new ListView(this);
            list = listaCreada;
        }
        if(listaOpciones==null) {
            listaOpciones = new ArrayList<>();
            listaOpciones.add("Desplazamiento");
            listaOpciones.add("Fuerzas Internas");
            listaOpciones.add("Reacciones");
        }
        adapter = new ListAdapterOpc(this, listaOpciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {

                    }
                    break;
                    case 1: {


                    }
                    break;
                    case 2: {
                   

                    }
                    break;

                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
