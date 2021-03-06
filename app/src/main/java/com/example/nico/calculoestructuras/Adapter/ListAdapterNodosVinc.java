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
public class ListAdapterNodosVinc extends BaseAdapter{
    Context context;
    ArrayList<Nudo> array;
    ArrayList<Vinculo> array2;
    LayoutInflater inflater;



    public ListAdapterNodosVinc(Context context, ArrayList<Nudo> array, ArrayList<Vinculo> array2)
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


    public Vinculo getVinculo(int position){
        return array2.get(position);
    }


    public ArrayList<Vinculo> addVinc(Vinculo v){
        array2.add(v);
        return array2;
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        view = View.inflate(context, R.layout.list_nudo_vinculo_item, null);
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
        if(array2.size()>0 && array2.size()>position) {
            if (array2.get(position).getRestX()!=0) {
                restEnX.setText("Rest en X: SI");
            } else{
                restEnX.setText("Rest en X: NO");
            }
            if (array2.get(position).getRestY()!=0) {
                restEnY.setText("Rest en Y: SI");
            } else{
                restEnY.setText("Rest en Y: NO");
            }
            if (array2.get(position).getRestGiro()!=0) {
                restEnGiro.setText("Rest en Rot: SI");
            }else{
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

    public void updateVinc(Vinculo v){
        int position = v.getNumNudo()-1;
        Vinculo vinculo = this.getVinculo(position);
        vinculo.setRestX(v.getRestX());
        vinculo.setRestY(v.getRestY());
        vinculo.setRestGiro(v.getRestGiro());
        array2.set(position,vinculo);
    }
}
