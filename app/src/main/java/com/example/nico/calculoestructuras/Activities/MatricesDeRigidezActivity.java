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
        listaOpciones = new ArrayList<>(Arrays.asList(
                "Matrices Elementales", "Matriz Global", "Matriz SFF", "Matriz SFR",
                "Matriz SRF", "Matriz SRR", "Vector AF", "Vector DR"
        ));
        adapter = new ListAdapterOpc(this, listaOpciones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatricesElementalesActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizGlobalActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizSFFActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizAFActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(MatricesDeRigidezActivity.this, MatrizAFActivity.class);
                        startActivity(intent);
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
