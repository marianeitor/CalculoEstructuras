package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 24/11/2015.
 */
public class ListAdapterOpc extends BaseAdapter{
    Context context;
    ArrayList<String> array;
    LayoutInflater inflater;



    public ListAdapterOpc(Context context, ArrayList<String> array)
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

        view = View.inflate(context, R.layout.list_opc_item, null);
        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.renglon);
        TextView titulo = (TextView) view.findViewById(R.id.nudos);
        ImageView numero= (ImageView) view.findViewById(R.id.uno);
        ImageView flecha = (ImageView) view.findViewById(R.id.flecha1);
        flecha.setImageResource(R.drawable.arrow);
        switch (position)
        {
            case 0:
            {
                titulo.setText(array.get(0));
                numero.setImageResource(R.drawable.number1);

            }break;
            case 1:
            {
                titulo.setText(array.get(1));
                numero.setImageResource(R.drawable.number2);
                Color c = new Color();
                linearLayout.setBackgroundColor(c.parseColor("#528881"));

            }break;
            case 2:
            {
                titulo.setText(array.get(2));
                numero.setImageResource(R.drawable.number3);

            }break;
            case 3:
            {
                titulo.setText(array.get(3));
                numero.setImageResource(R.drawable.number4);
                Color c = new Color();
                linearLayout.setBackgroundColor(c.parseColor("#528881"));

            }break;
            case 4:
            {
                titulo.setText(array.get(4));
                numero.setImageResource(R.drawable.number5);

            }break;
            case 5:
            {
                titulo.setText(array.get(5));
                numero.setImageResource(R.drawable.number6);
                Color c = new Color();
                linearLayout.setBackgroundColor(c.parseColor("#528881"));

            }break;
            case 6:
            {
                titulo.setText(array.get(6));
                numero.setImageResource(R.drawable.number7);

            }break;
            case 7:
            {
                titulo.setText(array.get(5));
                numero.setImageResource(R.drawable.number8);
                Color c = new Color();
                linearLayout.setBackgroundColor(c.parseColor("#528881"));

            }break;
            default:{}

        }
        return view;
    }

}
