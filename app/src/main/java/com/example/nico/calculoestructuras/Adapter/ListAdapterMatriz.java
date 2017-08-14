package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 19/11/2015.
 */
public class ListAdapterMatriz extends BaseAdapter {
    Context context;
    ArrayList<String> array;
    LayoutInflater inflater;



    public ListAdapterMatriz(Context context, ArrayList<String> array)
    {
        super();
        this.context= context;
        this.array= array;
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

        view = View.inflate(context, R.layout.list_matriz_item, null);
        TextView titulo =(TextView) view.findViewById(R.id.titulo_barras);
        TextView e = (TextView) view.findViewById(R.id.matrizElemental);
        CharSequence barra = titulo.getText();
        int idBarra = position+1;
        titulo.setText(barra+" "+ idBarra + " :");
        e.setText(""+array.get(position));


        return view;
    }


    public ArrayList<String> addItem(String b)
    {
        array.add(b);
        return array;
    }



    public ArrayList<String> removeItemByIndex(int index)
    {
        array.remove(index);
        return array;
    }
}
