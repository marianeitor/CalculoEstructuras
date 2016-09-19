package com.example.nico.calculoestructuras.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.R;

import java.util.ArrayList;

/**
 * Created by Nico on 25/11/2015.
 */
public class ListAdapterCargasBarras extends BaseAdapter {
    Context context;
    ArrayList<Barra> array;
    ArrayList<Conectividad> array2;
    ArrayList<CargaEnBarra> arrayCargaEnBarra;
    LayoutInflater inflater;



    public ListAdapterCargasBarras(Context context, ArrayList<Barra> array, ArrayList<Conectividad> array2, ArrayList<CargaEnBarra> arrCargEnBarra)
    {
        super();
        this.context= context;
        this.array= array;
        this.array2=array2;
        this.arrayCargaEnBarra = arrCargEnBarra;
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

        view = View.inflate(context, R.layout.list_cargas_barras_item, null);
        TextView titulo =(TextView) view.findViewById(R.id.titulo_barras);
        TextView conectividad = (TextView) view.findViewById(R.id.conectividad);
        conectividad.setText(" " + "NI:  " + array2.get(position).getNumNudoInicial()  + " NF:  " + array2.get(position).getNumNudoFinal());
        TextView cargaDist = (TextView) view.findViewById(R.id.nro_cargaDist);
        TextView cargaX = (TextView) view.findViewById(R.id.nro_cargaX);
        TextView cargaY = (TextView) view.findViewById(R.id.nro_cargaY);
        TextView cargaZ = (TextView) view.findViewById(R.id.nro_cargaZ);
        CharSequence barra = titulo.getText();
        int index = position+1;
        titulo.setText(barra +" "+ index + " :");

        if(this.getCargaAsociada(index) != null){
            CargaEnBarra carga = getCargaAsociada(index);
            if(carga.getCargaDistribuida() != 0){
                cargaDist.setText(Double.toString(carga.getCargaDistribuida()));
            }else{
                cargaDist.setText("-");
            }

            if(carga.getCargaPuntualEnX() != 0){
                cargaX.setText(Double.toString(carga.getCargaPuntualEnX()));
            }else{
                cargaX.setText("-");
            }

            if(carga.getCargaPuntualEnY() != 0){
                cargaY.setText(Double.toString(carga.getCargaPuntualEnY()));
            }else{
                cargaY.setText("-");
            }

            if(carga.getCargaPuntualEnZ() != 0){
                cargaZ.setText(Double.toString(carga.getCargaPuntualEnZ()));
            }else{
                cargaZ.setText("-");
            }
        }
        else{
            cargaDist.setText("-");
            cargaX.setText("-");
            cargaY.setText("-");
            cargaZ.setText("-");
        }
        return view;
    }

    public int getPosCarga(CargaEnBarra cargaEnBarra){
        for(int i=0 ; i < arrayCargaEnBarra.size() ; i++){
            if(arrayCargaEnBarra.get(i).getNumBarra() == cargaEnBarra.getNumBarra()){
                return i;
            }
        }
        return -1;
    }

    public CargaEnBarra getCargaAsociada(int nroBarra){
        if(arrayCargaEnBarra.size() == 0){
            return null;
        }else{
            for (CargaEnBarra carga:arrayCargaEnBarra) {
                if(carga.getNumBarra() == nroBarra){
                    return carga;
                }
            }
            return null;
        }

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

    public ArrayList<CargaEnBarra> addCargaBarra(CargaEnBarra carga) {
        arrayCargaEnBarra.add(carga);
        return arrayCargaEnBarra;
    }

    public void updateCargaBarra(CargaEnBarra cargaEnBarra) {
        int position = getPosCarga(cargaEnBarra);
        if(position != -1) {
            arrayCargaEnBarra.set(position,cargaEnBarra);
        }
    }
}
