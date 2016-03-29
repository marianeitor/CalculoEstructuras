package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 25/11/2015.
 */
public class ListAdapterBarrasCon extends BaseAdapter {
    Context context;
    ArrayList<Barra> array;
    LayoutInflater inflater;
    ArrayList<Conectividad> arrayConect;



    public ListAdapterBarrasCon(Context context, ArrayList<Barra> array, ArrayList<Conectividad> array2)
    {
        super();
        this.context= context;
        this.array= array;
        this.arrayConect=array2;
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
    public Conectividad getConect(int position){
        return arrayConect.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        view = View.inflate(context, R.layout.list_barra_conect_item, null);
        TextView titulo =(TextView) view.findViewById(R.id.titulo_barras);
        TextView conectividad = (TextView) view.findViewById(R.id.conectividad);
        if(arrayConect.size()>position) {
            conectividad.setText(" " + "NI:  " + arrayConect.get(position).getNumNudoInicial() + " NF:  " + arrayConect.get(position).getNumNudoFinal());
        }
        else {
            conectividad.setText(" " + "NI:  " + 0 + " NF:  " + 0);
        }
        TextView a = (TextView) view.findViewById(R.id.a);
        TextView e = (TextView) view.findViewById(R.id.e);
        TextView i = (TextView) view.findViewById(R.id.i);
        CharSequence barra = titulo.getText();
        int idBarra = array.get(position).getNumOrden();
        titulo.setText(barra +" "+ idBarra + " :");
        a.setText("" + array.get(position).getArea());
        e.setText(""+array.get(position).getElasticidad());
        i.setText("" + array.get(position).getInercia());

        return view;
    }


    public ArrayList<Barra> addItem(Barra b)
    {
        array.add(b);
        return array;
    }

    public ArrayList<Barra> removeItem(Barra b)
    {
        array.remove(b.getNumOrden());
        return array;
    }

    public ArrayList<Barra> removeItemByIndex(int index)
    {
        array.remove(index);
        return array;
    }
}
