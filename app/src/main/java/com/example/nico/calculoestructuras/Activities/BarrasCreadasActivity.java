package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
    private final int RESULT_BARRA=1;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void actionCargarOtraBarra (View view)
    {
        Intent i = new Intent(this,AgregarBarraActivity.class);
        startActivityForResult(i, RESULT_BARRA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case RESULT_BARRA:
                {
                    Barra barra = (Barra)data.getSerializableExtra("barra");
                    adapter.addItem(barra);
                    List.setAdapter(adapter);
                }break;

            }
        }
    }

    public void volverAMenu(View view)
    {
        this.finish();
    }

}
