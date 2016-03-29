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
    Nudo n;
    private final int REQUEST_VINC=1;

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
                int in = position +1;
                n = (Nudo)adapter.getItem(position);
                i.putExtra("nudo",n);
                startActivityForResult(i, REQUEST_VINC);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            switch (requestCode)
            {
                case REQUEST_VINC:
                {
                    Nudo nudo = (Nudo)data.getSerializableExtra("nudo");
                    n.setRestricciones(nudo.isRestriccionX(),nudo.isRestriccionY(),nudo.isRestriccionGiro());
                    adapter.notifyDataSetChanged();

                }break;

            }
        }

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {
         //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         //               .setAction("Action", null).show();
         //   }
        //});
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
