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
public class GestorEstructura extends OptionsMenuTitleOnly {
    PorticoPlano pp;
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

    public GestorEstructura(){
        /*
        //Grabo en BD las 3 barras
        for(int i=0; i<2;i++) {
            Barra b = new Barra(21000000.0, 15.0, 1500.0);
            ContentValues values = new ContentValues();
            values.put("elasticidad", b.getElasticidad());
            values.put("inercia", b.getInercia());
            values.put("area", b.getArea());
            DataBaseHelper.getDatabaseInstance(this).insertBarra(values);
        }
        //grabo en BD los 4 nudos
        Nudo n1= new Nudo(0,0);
        ContentValues valuesn1 = new ContentValues();
        valuesn1.put("coordx", n1.getX());
        valuesn1.put("coordy", n1.getY());
        DataBaseHelper.getDatabaseInstance(this).insertNudo(valuesn1);

        Nudo n2= new Nudo(5,5);
        ContentValues valuesn2 = new ContentValues();
        valuesn2.put("coordx", n2.getX());
        valuesn2.put("coordy", n2.getY());
        DataBaseHelper.getDatabaseInstance(this).insertNudo(valuesn2);

        //Nudo n3= new Nudo(10,5);
        //ContentValues valuesn3 = new ContentValues();
        //valuesn3.put("coordx", n3.getX());
        //valuesn3.put("coordy", n3.getY());
       // DataBaseHelper.getDatabaseInstance(this).insertNudo(valuesn3);

        Nudo n4= new Nudo(10,0);
        ContentValues valuesn4 = new ContentValues();
        valuesn4.put("coordx", n4.getX());
        valuesn4.put("coordy", n4.getY());
        DataBaseHelper.getDatabaseInstance(this).insertNudo(valuesn4);

        //Guardo las conectividades
        Conectividad c= new Conectividad(1,1,2);
        ContentValues valuesc1= new ContentValues();
        valuesc1.put("barraconectada", c.getNumBarra());
        valuesc1.put("niconec",c.getNumNudoInicial());
        valuesc1.put("nfconec", c.getNumNudoFinal());
        DataBaseHelper.getDatabaseInstance(this).insertConec(valuesc1);

        Conectividad c2= new Conectividad(2,2,3);
        ContentValues valuesc2= new ContentValues();
        valuesc2.put("barraconectada", c2.getNumBarra());
        valuesc2.put("niconec",c2.getNumNudoInicial());
        valuesc2.put("nfconec", c2.getNumNudoFinal());
        DataBaseHelper.getDatabaseInstance(this).insertConec(valuesc2);

        //Conectividad c3= new Conectividad(3,3,4);
        //ContentValues valuesc3= new ContentValues();
       // valuesc3.put("barraconectada", c3.getNumBarra());
       // valuesc3.put("niconec",c3.getNumNudoInicial());
       // valuesc3.put("nfconec", c3.getNumNudoFinal());
       // DataBaseHelper.getDatabaseInstance(this).insertConec(valuesc3);

        //Guardo los Vinculos
        Vinculo v= new Vinculo(2);
        v.setRestX(15000.0);
        v.setRestY(12000.0);
        v.setRestGiro(10000);
        ContentValues valuesv= new ContentValues();
        valuesv.put("nudovinculado", v.getNumNudo());
        valuesv.put("restx", v.getRestX());
        valuesv.put("resty", v.getRestY());
        valuesv.put("restgiro", v.getRestGiro());
        DataBaseHelper.getDatabaseInstance(this).insertVinculo(valuesv);

        //Guardo las cargas en las barras
        CargaEnBarra cb= new CargaEnBarra(1);
        ContentValues valuescb = new ContentValues();
        cb.setCargaPuntualEnY(15000.0);
        cb.setCargaPuntualEnY(12000.0);
        cb.setCargaPuntualEnZ(1000.0);
        valuescb.put("barracargada", cb.getNumBarra());
        valuescb.put("cargadistribuida", cb.getCargaDistribuida());
        valuescb.put("cargapuntualdistanudo", cb.getCargaPuntualDistXY());
        valuescb.put("cargapuntualenx", cb.getCargaPuntualEnX());
        valuescb.put("cargapuntualeny", cb.getCargaPuntualEnY());
        valuescb.put("cargapuntualenz", cb.getCargaPuntualEnZ());
        DataBaseHelper.getDatabaseInstance(this).insertCargaEnBarra(valuescb);

        //Guardo las cargas en Nudos
        CargaEnNudo cn= new CargaEnNudo(2);
        ContentValues valuescn= new ContentValues();
        cn.setCargaEnY(15000.0);
        cn.setCargaEnY(12000.0);
        cn.setCargaEnZ(10000.0);
        valuescn.put("nudoCargado", cn.getCargaEnX());
        valuescn.put("cargaenx", cn.getCargaEnY());
        valuescn.put("cargaeny", cn.getCargaEnY());
        valuescn.put("cargaenz", cn.getCargaEnZ());

        */

        //Genero las listas de objetos obtenidos desde la BD
        listaBarras= DataBaseHelper.getDatabaseInstance(this).getBarrasFromDB();
        listaNudos= DataBaseHelper.getDatabaseInstance(this).getNudosFromDB();
        listaConectividades= DataBaseHelper.getDatabaseInstance(this).getConecFromDB();
        listaVinculos= DataBaseHelper.getDatabaseInstance(this).getVinculoFromDB();
        listaCargasEnBarras= DataBaseHelper.getDatabaseInstance(this).getCargaEnBarraFromDB();
        listasCargasEnNudos=DataBaseHelper.getDatabaseInstance(this).getCargaEnNudoFromDB();

        //Genero objeto Portico Plano
        int cantBarras= listaBarras.size();
        int cantNudos= listaNudos.size();
        pp= new PorticoPlano(cantNudos,cantBarras);


        for (int i=0; i<cantNudos;i++){
            pp.crearNodo(i,listaNudos.get(i).getX(),listaNudos.get(i).getY(),false,false,false);
        }
        for (int j=0;j<cantBarras;j++){
            pp.crearBarra(j, listaConectividades.get(j).getNumNudoInicial(), listaConectividades.get(j).numNudoFinal, listaBarras.get(j).getElasticidad(),
                    listaBarras.get(j).getArea(), listaBarras.get(j).getInercia());
        }
        for(int k=0; k<cantBarras;k++){
            pp.crearConectividad(listaConectividades.get(k).getNumBarra(),listaConectividades.get(k).getNumNudoInicial(),listaConectividades.get(k).getNumNudoFinal());
        }
        for (int l=0; l<listaVinculos.size();l++){
            pp.crearVinculo(listaVinculos.get(l).getNumNudo(),listaVinculos.get(l).getRestX(),listaVinculos.get(l).getRestY(),listaVinculos.get(l).getRestGiro());
        }
        for(int m=0; m<listasCargasEnNudos.size();m++){
            pp.crearCargaNodo(listasCargasEnNudos.get(m).getNumNudo(),listasCargasEnNudos.get(m).getCargaEnX(),listasCargasEnNudos.get(m).getCargaEnY(),listasCargasEnNudos.get(m).getCargaEnZ());
        }

        this.cargarArray();
        this.resolucion();



    }

    public void resolucion(){
        sms=pp.loadS_Ms();
        alSFF=pp.makeSFF();
        alAF=pp.makeAF();
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
        if(pp.getfromidBarra(0)!=null) {
            alRigidez = new ArrayList<>();
            for (int i = 0; i < listaBarras.size(); i++) {
                alRigidez.add(i, pp.getfromidBarra(i).toString());
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
