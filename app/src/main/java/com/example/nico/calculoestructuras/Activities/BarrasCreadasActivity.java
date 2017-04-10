package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nico.calculoestructuras.Adapter.ListAdapterBarras;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class BarrasCreadasActivity extends AppCompatActivity {
    ListView List;
    ListAdapterBarras adapter;
    ArrayList<Barra> listaBarras;
    Barra b;
    private final int UPDATE_BARRA = 0;
    private final int NEW_BARRA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barras_creadas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List=(ListView) findViewById(R.id.lista_barras);
        listaBarras= DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        adapter = new ListAdapterBarras(this,listaBarras);
        List.setAdapter(adapter);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(BarrasCreadasActivity.this, AgregarBarraActivity.class);

                b = (Barra) adapter.getItem(position);
                i.putExtra("barra", b);
                i.putExtra("nroBarra", position+1);
                startActivityForResult(i, UPDATE_BARRA);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                android.app.FragmentManager fragmentManager = getFragmentManager();
                DialogGuardar dialogGuardar = new DialogGuardar();
                dialogGuardar.show(fragmentManager, "tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionCargarOtraBarra (View v)
    {
        Intent i = new Intent(this,AgregarBarraActivity.class);
        startActivityForResult(i, NEW_BARRA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Barra barra = (Barra)data.getSerializableExtra("barra");
            ContentValues values = new ContentValues();
            values.put("elasticidad", barra.getElasticidad());
            values.put("inercia", barra.getInercia());
            values.put("area", barra.getArea());

            switch (requestCode)
            {
                case UPDATE_BARRA:
                {
                    DataBaseHelper.getDatabaseInstance(this).updateBarra(values, (int)data.getSerializableExtra("nroBarra"));
                    listaBarras= DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
                    adapter = new ListAdapterBarras(this,listaBarras);
                    List.setAdapter(adapter);
                    break;
                }
                case NEW_BARRA:
                {
                    DataBaseHelper.getDatabaseInstance(this).insertBarra(values);
                    adapter.addItem(barra);
                    List.setAdapter(adapter);
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void volverAMenu(View view)
    {
        this.finish();
    }

}
