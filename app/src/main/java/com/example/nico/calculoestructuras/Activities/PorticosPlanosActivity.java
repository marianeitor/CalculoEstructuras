package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.nico.calculoestructuras.Adapter.ListAdapterOpc;
import com.example.nico.calculoestructuras.Backend.BackendOpc;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class PorticosPlanosActivity extends AppCompatActivity {
    ArrayList<String> listaOpciones;
    ListView list;
    ListAdapterOpc adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porticos_planos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listaopc);
        if(list == null)
        {
            ListView listaCreada = new ListView(this);
            list = listaCreada;
        }
        if(listaOpciones==null) {
            listaOpciones = BackendOpc.getInstance().getStringList();
        }
        adapter = new ListAdapterOpc(this, listaOpciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, NudosCreadosActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 1: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, BarrasCreadasActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 2: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, ConectividadesCreadasActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 3: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, VinculosCreadosActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case 4: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, CargasActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 5: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, AnalizarResultadosActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 6: {
                        Intent intent = new Intent(PorticosPlanosActivity.this, MatricesDeRigidezActivity.class);
                        startActivity(intent);

                    }
                    break;

                }
            }
        });

      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
