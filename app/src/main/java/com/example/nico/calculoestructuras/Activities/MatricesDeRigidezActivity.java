package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Adapter.ListAdapterOpc;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class MatricesDeRigidezActivity extends AppCompatActivity {
    ArrayList<String> listaOpciones;
    ListView list;
    ListAdapterOpc adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrices_de_rigidez);
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
            listaOpciones.add("Matrices Elementales");
            listaOpciones.add("Matriz Global");
            listaOpciones.add("Matriz SFF");
            listaOpciones.add("Matriz SFL");
            listaOpciones.add("Matriz SRF");
            listaOpciones.add("Matriz SRR");
            listaOpciones.add("Vector AF");
            listaOpciones.add("Vector DR");
        }
        adapter = new ListAdapterOpc(this, listaOpciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {

                        try {
                            Intent intent = new Intent(MatricesDeRigidezActivity.this, MatricesElementalesActivity.class);
                            startActivity(intent);
                        } catch (ArrayIndexOutOfBoundsException a) {
                            Toast.makeText(MatricesDeRigidezActivity.this, "Debe cargar valores primero", Toast.LENGTH_LONG).show();
                        } catch (SQLiteConstraintException e) {
                            Toast.makeText(MatricesDeRigidezActivity.this, "Debe cargar valores primero", Toast.LENGTH_LONG).show();
                        } catch (NullPointerException n) {
                            Toast.makeText(MatricesDeRigidezActivity.this, "Debe cargar valores primero", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                    case 1: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizGlobalActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 2: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 3: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 4: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 5: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 6: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizAFActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case 7: {
                        Intent intent = new Intent(MatricesDeRigidezActivity.this, MatrizAFActivity.class);
                        startActivity(intent);

                    }
                    break;

                }
            }
        });

     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
     //       @Override
      //      public void onClick(View view) {
      //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      //                  .setAction("Action", null).show();
      //      }
      //  });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
