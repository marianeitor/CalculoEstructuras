package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;
import com.example.nico.calculoestructuras.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Iniciar la Bd
        DataBaseHelper.getDatabaseInstance(this).inicializarBD();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void actionIngresar(View v)
    {
        Intent i = new Intent(MainActivity.this, OpcionesMetodoActivity.class);
        startActivity(i);
    }

    public void actionCargarDatos(View v){
        DataBaseHelper.getDatabaseInstance(this).precargarBD();
        Button btn = (Button)findViewById(R.id.cargarBD);
        btn.setClickable(false);
        Toast.makeText(this.getBaseContext(),"Datos cargados con éxito", Toast.LENGTH_SHORT);
        //Snackbar.make(v,"Datos cargados con éxito", Snackbar.LENGTH_SHORT);
    }
}
