package com.example.nico.calculoestructuras.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Adapter.ListAdapter;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

public class NudosCreadosActivity extends AppCompatActivity {
    ListView List;
    ListAdapter adapter;
    ArrayList<Nudo> listaNudos;
    Nudo n;
    private final int UPDATE_NUDO = 0;
    private final int NEW_NUDO=1;
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

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(NudosCreadosActivity.this, AgregarNudoActivity.class);

                n = (Nudo) adapter.getItem(position);
                i.putExtra("nudo", n);
                i.putExtra("nroNudo", position+1);
                startActivityForResult(i, UPDATE_NUDO);
            }
        });



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_save){
            Toast toast = Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //Snackbar.make(getCurrentFocus(), "Guardado", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void actionCargarOtroNudo(View v)
    {
        Intent i = new Intent(this,AgregarNudoActivity.class);
        startActivityForResult(i, NEW_NUDO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Nudo nudo = (Nudo)data.getSerializableExtra("nudo");
            ContentValues values = new ContentValues();
            values.put("coordx", nudo.getX());
            values.put("coordy", nudo.getY());

            switch (requestCode)
            {
                case UPDATE_NUDO:
                {
                    DataBaseHelper.getDatabaseInstance(this).updateNudo(values,(int)data.getSerializableExtra("nroNudo"));
                    listaNudos= DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
                    adapter = new ListAdapter(this, listaNudos);
                    List.setAdapter(adapter);
                    break;
                }
                case NEW_NUDO:
                {
                    DataBaseHelper.getDatabaseInstance(this).insertNudo(values);
                    adapter.addItem(nudo);
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
