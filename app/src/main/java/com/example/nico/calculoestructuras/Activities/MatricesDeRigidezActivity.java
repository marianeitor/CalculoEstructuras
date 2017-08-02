package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

import java.util.ArrayList;
import java.util.Arrays;

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
        if(list == null) {
            list = new ListView(this);
        }
        if(listaOpciones==null) {
            listaOpciones = new ArrayList<>(Arrays.asList(
                    "Matrices Elementales", "Matriz Global", "Matriz SFF", "Matriz SFR",
                    "Matriz SRF", "Matriz SRR", "Vector AF", "Vector DR"
            ));
//            listaOpciones.add("Matrices Elementales");
//            listaOpciones.add("Matriz Global");
//            listaOpciones.add("Matriz SFF");
//            listaOpciones.add("Matriz SFR");
//            listaOpciones.add("Matriz SRF");
//            listaOpciones.add("Matriz SRR");
//            listaOpciones.add("Vector AF");
//            listaOpciones.add("Vector DR");
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
                            Toast.makeText(MatricesDeRigidezActivity.this, "ArrayIndexOutOfBoundsException", Toast.LENGTH_LONG).show();
                        } catch (SQLiteConstraintException e) {
                            Toast.makeText(MatricesDeRigidezActivity.this, "SQLiteConstraintException", Toast.LENGTH_LONG).show();
                        } catch (NullPointerException n) {
                            Toast.makeText(MatricesDeRigidezActivity.this, "NullPointerException ", Toast.LENGTH_LONG).show();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
