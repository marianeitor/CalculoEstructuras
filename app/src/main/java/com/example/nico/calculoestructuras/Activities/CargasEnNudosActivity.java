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

import com.example.nico.calculoestructuras.Adapter.ListAdapterCargasNodos;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class CargasEnNudosActivity extends AppCompatActivity {
    ListView List;
    ListAdapterCargasNodos adapter;
    ArrayList<Nudo> listaNudos;
    ArrayList<Vinculo> listaVinculos;
    Nudo n;
    private final int REQUEST_CARGA_NODO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargas_en_nudos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_cargas_nudos);
        listaNudos= DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        listaVinculos=DataBaseHelper.getDatabaseInstance(this).getVinculoFromDB();
        adapter = new ListAdapterCargasNodos(this,listaNudos, listaVinculos);
        List.setAdapter(adapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CargasEnNudosActivity.this, AgregarCargaEnNudoActivity.class);
                int in = position + 1;
                n = (Nudo) adapter.getItem(position);
                i.putExtra("nudo", n);
                startActivityForResult(i, REQUEST_CARGA_NODO);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            switch (requestCode)
            {
                case REQUEST_CARGA_NODO:
                {
                    Nudo nudo = (Nudo)data.getSerializableExtra("nudo");
                    n.setRestricciones(nudo.isRestriccionX(),nudo.isRestriccionY(),nudo.isRestriccionGiro());
                    adapter.notifyDataSetChanged();

                }break;

            }
        }

    }

    public void volverAMenu (View view)
    {
        this.finish();
    }

}
