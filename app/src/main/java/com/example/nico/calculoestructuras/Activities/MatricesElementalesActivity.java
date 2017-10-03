package com.example.nico.calculoestructuras.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import com.example.nico.calculoestructuras.Adapter.ListAdapterMatriz;
import com.example.nico.calculoestructuras.Negocio.PorticoPlano2;
import com.example.nico.calculoestructuras.R;
import java.util.ArrayList;

public class MatricesElementalesActivity extends AppCompatActivity {
    PorticoPlano2 porticoPlano;
    ListView List;
    ListAdapterMatriz adapter;
    ArrayList<String> listaMatrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrices_elementales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        porticoPlano = (PorticoPlano2) getIntent().getSerializableExtra("portico");
        List = (ListView) findViewById(R.id.lista_matrices);
        listaMatrices = porticoPlano.getMatricesElementales();
        adapter = (listaMatrices != null) ? new ListAdapterMatriz(this, listaMatrices)
                : new ListAdapterMatriz(this, new ArrayList<String>());
        List.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
