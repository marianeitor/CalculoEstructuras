package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 25/11/2015.
 */
public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Nudo> array;
    LayoutInflater inflater;



    public ListAdapter(Context context, ArrayList<Nudo> array)
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

        view = View.inflate(context, R.layout.list_nudo_item, null);
        TextView nudo = (TextView) view.findViewById(R.id.nudo);
        TextView x = (TextView) view.findViewById(R.id.x_mostrar);
        TextView y = (TextView) view.findViewById(R.id.y_mostrar);
        CharSequence c = nudo.getText();
        int index = position +1;
        nudo.setText(c + " " + index);
        x.setText("" + array.get(position).getX());
        y.setText(""+array.get(position).getY());
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
