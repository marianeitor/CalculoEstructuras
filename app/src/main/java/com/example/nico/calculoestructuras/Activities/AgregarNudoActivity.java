package com.example.nico.calculoestructuras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nico.calculoestructuras.Backend.EjercicioActual;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;
import com.example.nico.calculoestructuras.xmlparser.XmlParser;

public class AgregarNudoActivity extends AppCompatActivity {
    EditText x;
    EditText y;
    int nroNudo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent i = getIntent();
        Nudo n = (Nudo)getIntent().getSerializableExtra("nudo");
        x = (EditText) findViewById(R.id.x_ingresada);
        y = (EditText) findViewById(R.id.y_ingresada);
        if(n != null){
            x.setText(Double.toString(n.getX()));
            y.setText(Double.toString(n.getY()));
            nroNudo = (int)getIntent().getSerializableExtra("nroNudo");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //todo: está comentado porque en este punto estaría inconsistente el ejercicio a guardar
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_save_as:
//                android.app.FragmentManager fragmentManager = getFragmentManager();
//                DialogGuardar dialogGuardar = new DialogGuardar();
//                dialogGuardar.show(fragmentManager, "tag");
//                return true;
            case R.id.action_save:
                String titulo = ((EjercicioActual)getApplicationContext()).getNombreEjercicio();
                final XmlParser xmlParser = new XmlParser(getApplicationContext());
                xmlParser.guardarEjercicio(titulo);
                Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void guardarNudo(View view)
    {
        String sx = x.getText().toString();
        String sy = y.getText().toString();
        if (sx.matches("") || sy.matches("")) {
            Toast.makeText(this, "Faltan valores de ingresar", Toast.LENGTH_SHORT).show();
        } else {
            Double double_x = Double.parseDouble(sx);
            Double double_y = Double.parseDouble(sy);
            Nudo n = new Nudo(double_x, double_y);

            Intent in = new Intent();
            in.putExtra("nudo", n);
            if(nroNudo != -1){
                in.putExtra("nroNudo",nroNudo);
            }
            setResult(RESULT_OK, in);
            this.finish();
        }
    }
    public void descartarNudo(View view)
    {
        this.finish();
    }
}
