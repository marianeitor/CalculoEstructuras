package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Adapter.ListAdapterOpc;
import com.example.nico.calculoestructuras.Backend.BackendOpc;
import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

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
        if(list == null) {
            list = new ListView(this);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_save_as:
//                android.app.FragmentManager fragmentManager = getFragmentManager();
//                DialogGuardar dialogGuardar = new DialogGuardar();
//                dialogGuardar.show(fragmentManager, "tag");
//                return true;
            case R.id.action_save:
                String titulo = ((EjercicioActual)getApplicationContext()).getNombreEjercicio();
                final XmlParser xmlParser = new XmlParser(getApplicationContext());
                xmlParser.guardarEjercicio(titulo);
                Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
