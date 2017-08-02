package com.example.nico.calculoestructuras.Negocio;

import com.example.nico.calculoestructuras.Activities.OptionsMenuTitleOnly;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nico on 9/11/2015.
 */
public class GestorEstructura2 extends OptionsMenuTitleOnly {
    PorticoPlano2 porticoPlano;
    ArrayList<String> alRigidez;
    double [][] alSFF;
    double [] alAF;
    ArrayList<Barra> listaBarras;
    ArrayList<Nudo> listaNudos;
    ArrayList<Conectividad> listaConectividades;
    ArrayList<Vinculo> listaVinculos;
    ArrayList<CargaEnBarra> listaCargasEnBarras;
    ArrayList<CargaEnNudo> listasCargasEnNudos;
    double [][] sms;

    public GestorEstructura2(){
        //Genero las listas de objetos obtenidos desde la BD
        listaBarras = DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        listaNudos = DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        listaConectividades = DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
        listaVinculos = DataBaseHelper.getDatabaseInstance(this).getVinculoFromDB();
        listaCargasEnBarras = DataBaseHelper.getDatabaseInstance(this).getCargaEnBarraFromDB();
        listasCargasEnNudos = DataBaseHelper.getDatabaseInstance(this).getCargaEnNudoFromDB();

        //Genero objeto Portico Plano
        int cantBarras = listaBarras.size();
        int cantNudos = listaNudos.size();
        porticoPlano = new PorticoPlano2(cantNudos,cantBarras, this);

//        // Todo: Consultar - Creo que estos for deberian hacerse directamente en la clase PorticoPlano
//        for (int i=0; i<cantNudos;i++){
//            porticoPlano.crearNodo(i,listaNudos.get(i).getX(),listaNudos.get(i).getY(),false,false,false);
//        }
//        for (int j=0;j<cantBarras;j++){
//            porticoPlano.crearBarra(j, listaConectividades.get(j).getNumNudoInicial(), listaConectividades.get(j).numNudoFinal, listaBarras.get(j).getElasticidad(),
//                    listaBarras.get(j).getArea(), listaBarras.get(j).getInercia());
//        }
//        for(int k=0; k<cantBarras;k++){
//            porticoPlano.crearConectividad(listaConectividades.get(k).getNumBarra(),listaConectividades.get(k).getNumNudoInicial(),listaConectividades.get(k).getNumNudoFinal());
//        }
//        for (int l=0; l<listaVinculos.size();l++){
//            porticoPlano.crearVinculo(listaVinculos.get(l).getNumNudo(),listaVinculos.get(l).getRestX(),listaVinculos.get(l).getRestY(),listaVinculos.get(l).getRestGiro());
//        }
//        for(int m=0; m<listasCargasEnNudos.size();m++){
//            porticoPlano.crearCargaNodo(listasCargasEnNudos.get(m).getNumNudo(),listasCargasEnNudos.get(m).getCargaEnX(),listasCargasEnNudos.get(m).getCargaEnY(),listasCargasEnNudos.get(m).getCargaEnZ());
//        }

        this.cargarArray();
        this.resolucion();



    }

    public void resolucion(){
        sms= porticoPlano.crear_Sms();
        alSFF= porticoPlano.makeSFF();
        alAF= porticoPlano.makeAF();
    }

    public String getGlobal(){
        String str="";
        for (int i=0; i<sms.length;i++){
            str+=" [ ";
            for (int j=0; j<sms[i].length;j++){
                double d =sms[i][j];
                BigDecimal big = new BigDecimal(d);
                big = big.setScale(2, RoundingMode.HALF_UP);
                str+=big;
                if(j!=sms[i].length-1){
                    str+=" , ";
                }else{
                    str+=" ] "+"\n";
                }
            }
            str+="\n";
        }
        return str;
    }

    public String getSFF(){
        String str="";
        for(int i=0; i<alSFF.length;i++){
            str+=" [ ";
            for (int j=0; j<alSFF[i].length;j++){
                double d =alSFF[i][j];
                BigDecimal big = new BigDecimal(d);
                big = big.setScale(2, RoundingMode.HALF_UP);
                str+=big;
                if(j!=alSFF[i].length-1){
                    str+=" , ";
                }else{
                    str+=" ] "+"\n";
                }
            }
            str+="\n";
        }
        return str;
    }
    public String getAlAF(){
        String str="[";
        for(int i=0; i<alAF.length;i++){
            double d =alAF[i];
            BigDecimal big = new BigDecimal(d);
            big = big.setScale(2, RoundingMode.HALF_UP);
            str+=big;
                if(i!=alSFF[i].length-1){
                    str+=" , ";
                }else{
                    str+="]";
                }
        }
        str+="\n";
        return str;
    }
    public void cargarArray(){
        if(porticoPlano.getfromidBarra(0)!=null) {
            alRigidez = new ArrayList<>();
            for (int i = 0; i < listaBarras.size(); i++) {
                alRigidez.add(i, porticoPlano.getfromidBarra(i).toString());
            }
        }
    }

    public ArrayList getAl(){
        if(alRigidez !=null){
            return alRigidez;}
        return null;
    }

    public ArrayList<Conectividad> ordenar(ArrayList<Conectividad> a){
        Iterator it = ((ArrayList) a).iterator();
        int i=0;
        while(it.hasNext()) {
            ((ArrayList) a).add(a.get(i).getNumBarra(),a.get(i));
            i++;
        }
        return (ArrayList) a;
    }

    @Override
    public String toString(){
        return "";
    }

}
