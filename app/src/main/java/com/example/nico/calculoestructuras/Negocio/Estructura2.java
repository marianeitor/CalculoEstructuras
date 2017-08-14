package com.example.nico.calculoestructuras.Negocio;

import android.content.Context;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nico on 9/11/2015.
 */
public class Estructura2 {
    private final int dof;
    private final int cantBarras;
    private final int cantNodos;
    private double[][] matrizGlobal;
    private int nk = 0;
    ArrayList<Barra> listaBarras;
    ArrayList<Nudo> listaNudos;
    ArrayList<Conectividad> listaConectividades;
    ArrayList<Vinculo> listaVinculos;
    ArrayList<CargaEnBarra> listaCargasEnBarras;
    ArrayList<CargaEnNudo> listasCargasEnNudos;

    public Estructura2(int n, int e, int d, Context context) {
        cantNodos=n;
        cantBarras=e;
        dof=d;
        listaBarras = DataBaseHelper.getDatabaseInstance(context).getBarrasFromDB();
        listaNudos = DataBaseHelper.getDatabaseInstance(context).getNudosFromDB();
        listaConectividades = DataBaseHelper.getDatabaseInstance(context).getConecFromDB();
        listaVinculos = DataBaseHelper.getDatabaseInstance(context).getVinculoFromDB();
        listaCargasEnBarras = DataBaseHelper.getDatabaseInstance(context).getCargaEnBarraFromDB();
        listasCargasEnNudos = DataBaseHelper.getDatabaseInstance(context).getCargaEnNudoFromDB();
        crearBarras();
        matrizGlobal = new double[dof * cantNodos][dof * cantNodos];
    }

    public double[][] getMatrizGlobal() {
        return matrizGlobal;
    }

    public void crearBarras() {
        for (Barra barra:listaBarras) {
            Conectividad conectividad = getFromIdBarraConectividad(barra.getNumOrden());
            Nudo nudoInicial = getfromidNudo(conectividad.getNumNudoInicial());
            Nudo nudoFinal = getfromidNudo(conectividad.getNumNudoFinal());
            double len = Math.sqrt((nudoInicial.getX() - nudoFinal.getX()) * (nudoInicial.getX() - nudoFinal.getX()) +
                    (nudoInicial.getY() - nudoFinal.getY()) * (nudoInicial.getY() - nudoFinal.getY()));
            double g1 = (nudoFinal.getX() - nudoInicial.getX()) / len; // cosenos directores
            double g2 = (nudoFinal.getY() - nudoInicial.getY()) / len;
            barra.construct(len, g1, g2);
        }
    }

    /**
     * Forma la matriz global a partir de las matrices elementales.
     * @return double[][]
     */
    public double[][] cargarMatrizGlobal(){
        for (int k = 0; k < cantBarras; ++k) {
            Conectividad con = getFromIdBarraConectividad(k+1);
            int nudoInicial = con.getNumNudoInicial();
            int nudoFinal = con.getNumNudoFinal();
            Barra barra = getfromidBarra(k+1);
            double[][] matrizElemental = barra.getMatrizElemental();
            double[][] primerCuadrante = new double[dof][dof];
            double[][] segundoCuadrante = new double[dof][dof];
            double[][] tercerCuadrante = new double[dof][dof];
            double[][] cuartoCuadrante = new double[dof][dof];
            for (int i = 0; i < dof; i++) {
                primerCuadrante[i] = Arrays.copyOfRange(matrizElemental[i], 0, dof);
            }
            copiarCuadrante(nudoInicial, nudoInicial, primerCuadrante);
            for (int i = 0; i < dof; i++) {
                segundoCuadrante[i] = Arrays.copyOfRange(matrizElemental[i], dof, matrizElemental[i].length);
            }
            copiarCuadrante(nudoInicial, nudoFinal, segundoCuadrante);
            for (int i = 0; i < dof; i++) {
                tercerCuadrante[i] = Arrays.copyOfRange(matrizElemental[i + dof], 0, dof);
            }
            copiarCuadrante(nudoFinal, nudoInicial, tercerCuadrante);
            for (int i = 0; i < dof; i++) {
                cuartoCuadrante[i] = Arrays.copyOfRange(matrizElemental[i + dof], dof, matrizElemental[i].length);
            }
            copiarCuadrante(nudoFinal, nudoFinal, cuartoCuadrante);
        }
        return matrizGlobal;
    }

    private void copiarCuadrante(int nudoFila, int nudoColumna, double[][] cuadrante) {
        int filaInicial = (nudoFila - 1) * dof;
        int columnaInicial = (nudoColumna -1) * dof;
        for (int i = 0; i < dof; ++i) {
            for (int j = 0; j < dof; ++j) {
                matrizGlobal[filaInicial + i][columnaInicial + j] += cuadrante[i][j];
            }
        }
    }

    /**
     * Obtiene la barra con el id dado.
     * @param id Id de la barra
     * @return Barra|null
     */
    public Barra getfromidBarra(int id) {
        for (Barra barra:listaBarras) {
            if(barra.getNumOrden() == id)
                return barra;
        }
        return null;
    }

    /**
     * Obtiene la conectividad correspondiente a la barra con el id dado.
     * @param id Id de la barra relacionada
     * @return Conectividad|null
     */
    public Conectividad getFromIdBarraConectividad(int id){
        for (Conectividad conectividad:listaConectividades) {
            if(conectividad.getNumBarra() == id)
                return conectividad;
        }
        return null;
    }

    /**
     * Obtiene el vinculo correspondiente al nudo con el id dado.
     * @param id Id del nudo relacionado
     * @return Vinculo|null
     */
    public Vinculo getFromIdNudoVinculo(int id){
        for (Vinculo vinculo:listaVinculos) {
            if(vinculo.getNumNudo() == id)
                return vinculo;
        }
        return null;
    }

    /**
     * Obtiene el nudo con el id dado.
     * @param  id Id del nudo
     * @return Nudo|null
     */
    public Nudo getfromidNudo(int id) {
        for (Nudo nudo:listaNudos) {
            if(nudo.getnOrden() == id)
                return nudo;
        }
        return null;
    }

    /**
     * Obtiene la carga en el nudo con el id dado.
     * @param  id Id del nudo relacionado
     * @return CargaEnNudo|null
     */
    public CargaEnNudo getFromIdNudoCarga(int id){
        for (CargaEnNudo cargaNudo:listasCargasEnNudos) {
            if(cargaNudo.getNumNudo() == id)
                return cargaNudo;
        }
        return null;
    }

    public int[] makeKRestGlob(){
        int[] restglob = new int[dof * cantNodos];
        for (int i = 0; i < cantNodos; ++i) {
            Vinculo vin= getFromIdNudoVinculo(i+1);
                if (vin.getRestX()!=0) {
                    restglob[dof * i ] = -1;
                } else {
                    restglob[dof * i ] = nk++;
                }
                if(vin.getRestY()!=0){
                    restglob[dof * i + 1]=-1;
                }else{
                    restglob[dof * i + 1] = nk++;
                }
                if(vin.getRestGiro()!=0){
                    restglob[dof * i + 2]=-1;
                }else{
                    restglob[dof * i + 2] = nk++;
                }
        }
    return restglob;
    }

//    public double [][] makeSFF(){

//        int[] restG= makeKRestGlob();
//        double [][] kred = new double[nk][nk];
//
//        for (int i = 0; i < dof * cantNodos; ++i) {
//            if (restG[i] != -1) {
//                for (int j = 0; j < dof * cantNodos; ++j) {
//                    if (restG[j] != -1) {
//                        kred[restG[i]][restG[j]] = getMatrizGlobal()[i][j];
//                    }
//                }
//            }
//        }
//        return kred;
//    }
//
//    public double[] makeAF(){//No se como toma las fuerzas
//        nk=0;
//        int[] restG= makeKRestGlob();
//        double [] pred = new double[nk];
////        for (int i = 0; i < dof * cantNodos; ++i) {
////            if (restG[i] != -1) {
////                int idj = i / 3;
////                int idf = i % 3;
////                //pred[makeKRestGlob()[i]] = getfromidNudo(idj).getF()[idf];
////                pred[restG[i]] = getFromIdNudoCarga(idj+1).getCargaEnX();
////            }
////        }
//        return pred;
//    }

    @Override
    public String toString() {
        return "Estructura{" + "listaBarras=" + listaBarras + ", listaNudos=" + listaNudos + '}';
    }
}
