package com.example.nico.calculoestructuras.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

import java.io.File;
import java.util.ArrayList;

public class SeleccionarEjercicioActivity extends AppCompatActivity {

    private ArrayList<String> item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ejercicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        item = buscarArchivos();

        //Localizamos y llenamos la lista
        final ListView lstOpciones = (ListView) findViewById(R.id.lista_ejercicios);
        ArrayAdapter<String> fileList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        lstOpciones.setAdapter(fileList);

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataBaseHelper.getDatabaseInstance(getBaseContext()).inicializarBD();
                XmlParser xmlParser = new XmlParser(getBaseContext());
                xmlParser.cargarEjercicio(lstOpciones.getItemAtPosition(i).toString());
                Intent intent = new Intent(SeleccionarEjercicioActivity.this, OpcionesMetodoActivity.class);
                startActivity(intent);
                SeleccionarEjercicioActivity.this.finish();
            }
        });

        lstOpciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Nombre del archivo con la extensión .xml
                final String fileName = lstOpciones.getItemAtPosition(i).toString() + ".xml";
                AlertDialog.Builder builder = new AlertDialog.Builder(SeleccionarEjercicioActivity.this);
                builder.setMessage("¿Desea eliminar este archivo?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                File file = new File(getBaseContext().getFilesDir() + "/" + fileName);
                                if (file.delete()){
                                    Toast.makeText(SeleccionarEjercicioActivity.this, "El archivo fue eliminado", Toast.LENGTH_SHORT).show();
                                    ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(SeleccionarEjercicioActivity.this, android.R.layout.simple_list_item_1, buscarArchivos());
                                    lstOpciones.setAdapter(newAdapter);
                                } else {
                                    Toast.makeText(SeleccionarEjercicioActivity.this, "No se pudo eliminar el archivo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                builder.create();
                builder.show();
                return true;
            }
        });


    }

    private ArrayList<String> buscarArchivos(){
        File f = new File(getBaseContext().getFilesDir().toString());
        File[] files = f.listFiles();
        item = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory())
                    // Nombre del archivo sin la extensión .xml
                    item.add(file.getName().substring(0, file.getName().indexOf(".")));
            }
        }
        if(item.isEmpty()){
            Toast.makeText(getBaseContext(), "No se encontraron ejercicios guardados", Toast.LENGTH_SHORT).show();
        }
        return item;
    }



}
