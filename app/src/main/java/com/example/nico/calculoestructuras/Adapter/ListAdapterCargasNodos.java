package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    ArrayList<Vinculo> array2;
    LayoutInflater inflater;



    public ListAdapterCargasNodos(Context context, ArrayList<Nudo> array, ArrayList<Vinculo> array2)
    {
        super();
        this.context= context;
        this.array= array;
        this.array2=array2;
        this.inflater=((Activity)context).getLayoutInflater();
    }



    @Override
    public int getCount() {
        return array.size();
    }


    @Override
    public Object getItem(int position) {
        return array.get(position);
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
        if(array2.size()>0) {
            if (array2.get(position).getRestX()!=0) {
                restEnX.setText("Rest en X: SI");
            }
            if (array2.get(position).getRestX()==0) {
                restEnX.setText("Rest en X: NO");
            }
            if (array2.get(position).getRestY()!=0) {
                restEnY.setText("Rest en Y: SI");
            }
            if (array2.get(position).getRestY()==0) {
                restEnY.setText("Rest en Y: NO");
            }
            if (array2.get(position).getRestGiro()!=0) {
                restEnGiro.setText("Rest en Rot: SI");
            }
            if (array2.get(position).getRestGiro()==0) {
                restEnGiro.setText("Rest en Rot: NO");
            }
        }
        return view;
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
}
