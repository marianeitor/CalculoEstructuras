package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterMatriz;
import com.example.nico.calculoestructuras.Negocio.GestorEstructura2;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class MatricesElementalesActivity extends AppCompatActivity {
    GestorEstructura2 gestorEstructura;
    ListView List;
    ListAdapterMatriz adapter;
    ArrayList<String> listaMatrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrices_elementales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gestorEstructura = new GestorEstructura2();
        List=(ListView) findViewById(R.id.lista_matrices);
        listaMatrices = gestorEstructura.getMatricesElementales();
        adapter = (listaMatrices != null) ? new ListAdapterMatriz(this, listaMatrices)
                : new ListAdapterMatriz(this, new ArrayList<String>());
        List.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
