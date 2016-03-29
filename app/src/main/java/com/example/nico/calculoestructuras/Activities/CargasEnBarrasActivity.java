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

import com.example.nico.calculoestructuras.Adapter.ListAdapterCargasBarras;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class CargasEnBarrasActivity extends AppCompatActivity {
    ListView List;
    ListAdapterCargasBarras adapter2;
    ArrayList<Barra> listaBarras;
    ArrayList<Conectividad> listaConectividad;
    private final int REQUEST_CARGA_BARRA=1;
    Barra b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargas_en_barras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_cargas_barras);
        listaBarras= DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        listaConectividad=DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
        adapter2 = new ListAdapterCargasBarras(this,listaBarras,listaConectividad);
        List.setAdapter(adapter2);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CargasEnBarrasActivity.this, AgregarCargaEnBarraActivity.class);
                b = (Barra) adapter2.getItem(position);
                i.putExtra("barra", b);
                startActivityForResult(i, REQUEST_CARGA_BARRA);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void volverAMenu(View view)
    {
        this.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CARGA_BARRA:
                {
                    Barra barra = (Barra)data.getSerializableExtra("barra");
                    //b.setNudoFinal(barra.getNudoFinal());
                    //b.setNudoInicial(barra.getNudoInicial());
                    adapter2.notifyDataSetChanged();
                }break;

            }
        }
    }

}
