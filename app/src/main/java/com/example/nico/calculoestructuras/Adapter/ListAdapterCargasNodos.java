package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 25/11/2015.
 */
public class ListAdapterCargasNodos extends BaseAdapter {
    Context context;
    ArrayList<Nudo> array;
    ArrayList<CargaEnNudo> array2;
    LayoutInflater inflater;



    public ListAdapterCargasNodos(Context context, ArrayList<Nudo> array, ArrayList<CargaEnNudo> array2)
    {
        super();
        this.context = context;
        this.array = array;
        this.array2 = array2;
        this.inflater = ((Activity)context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return array.size();
    }


    @Override
    public Object getItem(int position) {
        return array.get(position);
    }


    public CargaEnNudo getCargaNudo(int position){
        return array2.get(position);
    }


    public ArrayList<CargaEnNudo> addCargaNudo(CargaEnNudo carga){
        array2.add(carga);
        return array2;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        view = View.inflate(context, R.layout.list_nudo_cargas_item, null);
        TextView nudo = (TextView) view.findViewById(R.id.nudo);
        TextView x = (TextView) view.findViewById(R.id.x_mostrar);
        TextView y = (TextView) view.findViewById(R.id.y_mostrar);
        CharSequence c = nudo.getText();
        int index = position +1;
        nudo.setText(c + " " + index);
        x.setText("" + array.get(position).getX());
        y.setText(""+array.get(position).getY());
        TextView restEnX = (TextView) view.findViewById(R.id.rest_X);
        TextView restEnY = (TextView) view.findViewById(R.id.rest_Y);
        TextView restEnGiro = (TextView) view.findViewById(R.id.rest_giro);

        //vinculo.setText(" " + "Rest x:  " + array.get(position).isRestriccionX()  + " Rest y:  " + array.get(position).isRestriccionY()+" Rest Giro: "+array.get(position).isRestriccionGiro() );
        if(this.getCargaAsociada(index) != null) {
            CargaEnNudo carga = getCargaAsociada(index);
            if (carga.getCargaEnX()!=0) {
                restEnX.setText("Carga en X: SI");
            }
            if (carga.getCargaEnX()==0) {
                restEnX.setText("Carga en X: NO");
            }
            if (carga.getCargaEnY()!=0) {
                restEnY.setText("Carga en Y: SI");
            }
            if (carga.getCargaEnY()==0) {
                restEnY.setText("Carga en Y: NO");
            }
            if (carga.getCargaEnZ()!=0) {
                restEnGiro.setText("Carga en Z: SI");
            }
            if (carga.getCargaEnZ()==0) {
                restEnGiro.setText("Carga en Z: NO");
            }
        }
        return view;
    }

    public CargaEnNudo getCargaAsociada(int nroNudo) {
        if(array2.size() == 0){
            return null;
        }else{
            for (CargaEnNudo carga:array2) {
                if(carga.getNumNudo() == nroNudo){
                    return carga;
                }
            }
            return null;
        }
    }

    public int getPosCarga(CargaEnNudo carga){
        for(int i=0 ; i < array2.size() ; i++){
            if(array2.get(i).getNumNudo() == carga.getNumNudo()){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Nudo> removeItem(int position)
    {
        array.remove(position);
        return array;
    }


    public ArrayList<Nudo> addItem(Nudo n)
    {
        array.add(n);
        return array;
    }

    public void updateCargaNodo(CargaEnNudo carga){
        int position = this.getPosCarga(carga);
        if(position != -1) {
            array2.set(position,carga);
        }
    }
}
