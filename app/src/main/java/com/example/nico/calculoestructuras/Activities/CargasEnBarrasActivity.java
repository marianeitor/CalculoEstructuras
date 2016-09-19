package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterCargasBarras;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class CargasEnBarrasActivity extends AppCompatActivity {
    ListView List;
    ListAdapterCargasBarras adapter2;
    ArrayList<Barra> listaBarras;
    ArrayList<Conectividad> listaConectividad;
    ArrayList<CargaEnBarra> listaCargasEnBarras;
    private final int UPDATE_CARGA = 0;
    private final int NEW_CARGA = 1;
    Barra b;
    CargaEnBarra carga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargas_en_barras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_cargas_barras);
        listaBarras = DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        listaConectividad = DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
        listaCargasEnBarras = DataBaseHelper.getDatabaseInstance(this).getCargaEnBarraFromDB();
        adapter2 = new ListAdapterCargasBarras(this,listaBarras,listaConectividad,listaCargasEnBarras);
        List.setAdapter(adapter2);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CargasEnBarrasActivity.this, AgregarCargaEnBarraActivity.class);
                b = (Barra) adapter2.getItem(position);
                // Compara la posicion del item seleccionado con el tamaño de listaCargasBarras para saber si hay alguna
                // carga que se corresponda con ese barra
                if(adapter2.getCargaAsociada(b.getNumOrden()) != null){ //En caso de haber, busca esa carga
                    carga = adapter2.getCargaAsociada(b.getNumOrden());
                    i.putExtra("barra", b);
                    i.putExtra("carga", carga);
                    startActivityForResult(i, UPDATE_CARGA);
                }else{ //De lo contrario, crea una nueva
                    carga = new CargaEnBarra(b.getNumOrden());
                    i.putExtra("barra", b);
                    startActivityForResult(i, NEW_CARGA);
                }

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
            CargaEnBarra cargaEnBarra = (CargaEnBarra)data.getSerializableExtra("carga");
            ContentValues values = new ContentValues();
            values.put("barracargada", cargaEnBarra.getNumBarra());
            values.put("cargadistribuida", cargaEnBarra.getCargaDistribuida());
            values.put("cargapuntualenx", cargaEnBarra.getCargaPuntualEnX());
            values.put("cargapuntualeny", cargaEnBarra.getCargaPuntualEnY());
            values.put("cargapuntualdistxy", cargaEnBarra.getCargaPuntualDistXY());
            values.put("cargapuntualenz", cargaEnBarra.getCargaPuntualEnZ());
            values.put("cargapuntualdistz", cargaEnBarra.getCargaPuntualDistXY());

            switch (requestCode)
            {
                case NEW_CARGA:
                {// En caso de ser una nueva carga la crea en la bd y la agrega a la listaCargas del adapter
                    DataBaseHelper.getDatabaseInstance(this).insertCargaEnBarra(values);
                    adapter2.addCargaBarra(cargaEnBarra);
                    break;
                }
                case UPDATE_CARGA:
                {
                    DataBaseHelper.getDatabaseInstance(this).updateCargaBarra(values, cargaEnBarra.getNumBarra());
                    adapter2.updateCargaBarra(cargaEnBarra);
                    break;
                }

            }
            adapter2.notifyDataSetChanged();
        }
    }

}
