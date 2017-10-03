package com.example.nico.calculoestructuras.Negocio;

import com.example.nico.calculoestructuras.Activities.OptionsMenuTitleOnly;
import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nico on 9/11/2015.
 * @deprecated
 */
public class GestorEstructura2 extends OptionsMenuTitleOnly implements Serializable {

    PorticoPlano2 porticoPlano;

    /** matricesElementales: Contiene las matrices de todas las barras**/
    ArrayList<String> matricesElementales;
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
        porticoPlano = new PorticoPlano2(this);

//        this.cargarMatricesElementales();
        this.resolucion();



    }

    public void resolucion() {
        sms = porticoPlano.getMatrizGlobal();
//        alSFF= porticoPlano.makeSFF();
//        alAF= porticoPlano.makeAF();
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

//    /**
//     * Reune las matrices elementales de cada barra en una variable general
//     */
//    public void cargarMatricesElementales(){
//        if(porticoPlano.listaBarras.size() != 0) {
//            matricesElementales = new ArrayList<>();
//            for (int i = 1; i <= listaBarras.size(); i++) {
//                matricesElementales.add(porticoPlano.getfromidBarra(i).toString());
//            }
//        }
//    }

//    /**
//     * Obtiene las matrices elementales
//     * @return double[][] | null
//     */
//    public ArrayList<String> getMatricesElementales(){
//        if(matricesElementales !=null){
//            return matricesElementales;}
//        return null;
//    }

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
