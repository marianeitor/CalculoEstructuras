package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterBarrasCon;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class ConectividadesCreadasActivity extends AppCompatActivity {
    ListView List;
    ListAdapterBarrasCon adapter2;
    ArrayList<Barra> listaBarras;
    ArrayList<Conectividad> listaConectividad;
    private final int UPDATE_CONEC = 0;
    private final int NEW_CONEC = 1;
    Barra b;
    Conectividad c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectividades_creadas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_barras_conect);
        listaBarras = DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        listaConectividad = DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
        adapter2 = new ListAdapterBarrasCon(this,listaBarras,listaConectividad);
        //adapter = new ListAdapterBarras(this,listaBarras);
        List.setAdapter(adapter2);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CargaConectividadesActivity.this, "probando bd", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ConectividadesCreadasActivity.this, AgregarConectividadActivity.class);
                //int in = position + 1;
                b = (Barra) adapter2.getItem(position);
                // Compara la posicion del item seleccionado con el tamaño de listaConectividad para saber si hay alguna
                // conectividad que se corresponda con esa barra
                if (listaConectividad.size() > position) { //En caso de haber, busca esa conectividad
                    c = (Conectividad) adapter2.getConect(position);
                    i.putExtra("barra", b);
                    i.putExtra("n_ini", c.getNumNudoInicial());
                    i.putExtra("n_fin", c.getNumNudoFinal());
                    startActivityForResult(i, UPDATE_CONEC);
                } else { // De lo contrario crea una nueva
                    c = new Conectividad(position + 1, 0, 0);
                    i.putExtra("barra", b);
                    i.putExtra("n_ini", c.getNumNudoInicial());
                    i.putExtra("n_fin", c.getNumNudoFinal());
                    startActivityForResult(i, NEW_CONEC);
                }
               // i.putExtra("barra", b);
                // i.putExtra("numero",in);
               // startActivityForResult(i, NEW_CONEC);
            }
        });

     //   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View view) {
       ///         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         //               .setAction("Action", null).show();
        //    }
       // });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void volverAMenu(View view)
    {
        this.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Conectividad con = (Conectividad)data.getSerializableExtra("conectividad");
            c.setNumNudoFinal(con.getNumNudoFinal());
            c.setNumNudoInicial(con.getNumNudoInicial());
            ContentValues values = new ContentValues();
            values.put("barraconectada", con.getNumBarra());
            values.put("niconec", con.getNumNudoInicial());
            values.put("nfconec", con.getNumNudoFinal());

            switch (requestCode)
            {

                case NEW_CONEC:
                {  // En caso de ser una nueva conectividad la crea en la bd y la agrega a la listaConectividades del adapter
                    DataBaseHelper.getDatabaseInstance(this).insertConec(values);
                    adapter2.addConec(c);
                    break;
                }

                case UPDATE_CONEC:
                { // En caso de una conectividad existente la actualiza en la bd
                    DataBaseHelper.getDatabaseInstance(this).updateConec(values,con.getNumBarra());
                    listaBarras = DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
                    listaConectividad = DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
                    adapter2 = new ListAdapterBarrasCon(this,listaBarras,listaConectividad);
                    //adapter = new ListAdapterBarras(this,listaBarras);
                    List.setAdapter(adapter2);
                    break;
                }

            }

            adapter2.notifyDataSetChanged();
        }
    }

}
