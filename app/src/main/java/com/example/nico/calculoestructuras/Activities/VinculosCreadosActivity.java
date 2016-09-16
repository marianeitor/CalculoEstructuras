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

import com.example.nico.calculoestructuras.Adapter.ListAdapterNodosVinc;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class VinculosCreadosActivity extends AppCompatActivity {
    ListView List;
    ListAdapterNodosVinc adapter;
    ArrayList<Nudo> listaNudos;
    ArrayList<Vinculo> listaVinculos;
    Nudo nudo;
    Vinculo vinculo;
    private final int UPDATE_VINC = 0;
    private final int NEW_VINC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinculos_creados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_nudos_vinc);
        listaNudos= DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        listaVinculos= DataBaseHelper.getDatabaseInstance(this).getVinculoFromDB();
        adapter = new ListAdapterNodosVinc(this,listaNudos, listaVinculos);
        List.setAdapter(adapter);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CargaConectividadesActivity.this, "probando bd", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VinculosCreadosActivity.this, AgregarVinculoActivity.class);
                //int in = position +1;
                nudo = (Nudo) adapter.getItem(position);
                // Compara la posicion del item seleccionado con el tamaño de listaVinculos para saber si hay algun
                // vinculo que se corresponda con ese nudo
                if(listaVinculos.size() > position){ //En caso de haber, busca ese vinculo
                    vinculo = (Vinculo)adapter.getVinculo(position);
                    i.putExtra("nudo", nudo);
                    i.putExtra("vinculo", vinculo);
                    startActivityForResult(i, UPDATE_VINC);
                } else{ //De lo contrario, crea una nueva
                    vinculo = new Vinculo(position+1);
                    i.putExtra("nudo", nudo);
                    startActivityForResult(i, NEW_VINC);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void volverAMenu(View view){
        this.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Vinculo v = (Vinculo)data.getSerializableExtra("vinc");
           // vinculo.setNumNudo(v.getNumNudo());
            //vinculo.setRestX(v.getRestX());
            //vinculo.setRestY(v.getRestY());
           // vinculo.setRestGiro(v.getRestGiro());

            ContentValues vincValues = new ContentValues();
            vincValues.put("nudovinculado",v.getNumNudo());
            vincValues.put("restx", v.getRestX());
            vincValues.put("resty", v.getRestY());
            vincValues.put("restgiro", v.getRestGiro());

            //Nudo n = (Nudo)data.getExtras().getSerializable("nudo");

            //ContentValues nudoValues = new ContentValues();

            switch (requestCode)
            {
                case NEW_VINC:
                {// En caso de ser un nuevo vinculo lo crea en la bd y lo agrega a la listaVinculos del adapter
                    DataBaseHelper.getDatabaseInstance(this).insertVinculo(vincValues);
                    adapter.addVinc(v);
                }break;

                case UPDATE_VINC:
                {// En caso de un vinculo existente la actualiza en la bd
                    DataBaseHelper.getDatabaseInstance(this).updateVinc(vincValues,v.getNumNudo());
                    adapter.updateVinc(v);
                }break;

            }
            nudo.setRestricciones((v.getRestX() != 0), (v.getRestY() != 0), (v.getRestGiro() != 0));
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
