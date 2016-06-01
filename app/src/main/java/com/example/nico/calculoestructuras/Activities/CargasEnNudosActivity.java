package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
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
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class CargasEnNudosActivity extends AppCompatActivity {
    ListView List;
    ListAdapterCargasNodos adapter;
    ArrayList<Nudo> listaNudos;
    ArrayList<CargaEnNudo> listaCargasNudos;
    Nudo n;
    CargaEnNudo carga;
    private final int UPDATE_CARGA = 0;
    private final int NEW_CARGA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargas_en_nudos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List =(ListView) findViewById(R.id.lista_cargas_nudos);
        listaNudos = DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        listaCargasNudos = DataBaseHelper.getDatabaseInstance(this).getCargaEnNudoFromDB();
        adapter = new ListAdapterCargasNodos(this,listaNudos, listaCargasNudos);
        List.setAdapter(adapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CargasEnNudosActivity.this, AgregarCargaEnNudoActivity.class);
                //int in = position + 1;
                n = (Nudo) adapter.getItem(position);
                // Compara la posicion del item seleccionado con el tamaño de listaCargasNudos para saber si hay alguna
                // carga que se corresponda con ese nudo
                if(listaCargasNudos.size() > position){ //En caso de haber, busca ese vinculo
                    carga = adapter.getCargaNudo(position);
                    i.putExtra("nudo", n);
                    startActivityForResult(i, UPDATE_CARGA);
                } else{ //De lo contrario, crea una nueva
                    carga = new CargaEnNudo(position+1);
                    i.putExtra("nudo", n);
                    startActivityForResult(i, NEW_CARGA);
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void volverAMenu (View view)
    {
        this.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            CargaEnNudo c = (CargaEnNudo) data.getSerializableExtra("carga");
            // vinculo.setNumNudo(v.getNumNudo());
            //vinculo.setRestX(v.getRestX());
            //vinculo.setRestY(v.getRestY());
            // vinculo.setRestGiro(v.getRestGiro());

            ContentValues cargaValues = new ContentValues();
            cargaValues.put("nudoCargado",c.getNumNudo());
            cargaValues.put("cargaenx", c.getCargaEnX());
            cargaValues.put("cargaeny", c.getCargaEnY());
            cargaValues.put("cargaenz", c.getCargaEnZ());

            //Nudo n = (Nudo)data.getExtras().getSerializable("nudo");

            //ContentValues nudoValues = new ContentValues();

            switch (requestCode)
            {
                case NEW_CARGA:
                {// En caso de ser un nuevo vinculo lo crea en la bd y lo agrega a la listaVinculos del adapter
                    DataBaseHelper.getDatabaseInstance(this).insertCargaEnNudo(cargaValues);
                    adapter.addCargaNudo(c);
                }break;

                case UPDATE_CARGA:
                {// En caso de un vinculo existente la actualiza en la bd
                    DataBaseHelper.getDatabaseInstance(this).updateCargaNudo(cargaValues,c.getNumNudo());
                    adapter.updateCargaNodo(c);
                }break;

            }
            //nudo.setRestricciones((v.getRestX() != 0), (v.getRestY() != 0), (v.getRestGiro() != 0));
            adapter.notifyDataSetChanged();

        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //   public void onClick(View view) {
        //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //               .setAction("Action", null).show();
        //   }
        //});
    }
}
