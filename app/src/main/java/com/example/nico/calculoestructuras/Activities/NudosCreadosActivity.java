package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.nico.calculoestructuras.Adapter.ListAdapter;
import android.widget.ListView;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class NudosCreadosActivity extends AppCompatActivity {
    ListView List;
    ListAdapter adapter;
    ArrayList<Nudo> listaNudos;
    private final int RESULT_NUDO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nudos_creados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntent();

        List = (ListView) findViewById(R.id.lista);
        listaNudos= DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        adapter = new ListAdapter(this, listaNudos);
        List.setAdapter(adapter);



      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
      //      @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
       //     }
       // });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void actionCargarOtroNudo(View v)
    {
        Intent i = new Intent(this,AgregarNudoActivity.class);
        startActivityForResult(i, RESULT_NUDO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case RESULT_NUDO:
                {
                    Nudo n = (Nudo)data.getSerializableExtra("nudo");
                    adapter.addItem(n);
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
