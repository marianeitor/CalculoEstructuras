package com.example.nico.calculoestructuras.Negocio;

import android.content.Context;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;

import java.util.ArrayList;

/**
 * Created by Nico on 9/11/2015.
 */
public class Estructura2 {
    private final int dof;
    private final int cantBarras;
    private final int cantNodos;
    private double[][] s_MS;
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
    }

    public double[][] getS_MS() {
        return s_MS;
    }

    public void makeS_Ms() {
        s_MS = new double[dof * cantNodos][dof * cantNodos];
        for (int i = 0; i < dof * cantNodos; ++i) {
            for (int h = 0; h < dof * cantNodos; ++h) {
                s_MS[i][h] = 0;
            }
        }
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

//Agregar validacion
    public double[][] loadS_Ms(){
        this.makeS_Ms();
        for (int k = 0; k < cantBarras; ++k) {
            Conectividad con = getFromIdBarraConectividad(k);
            int ni = con.getNumNudoInicial();
            int nf = con.getNumNudoFinal();
            Barra bar = getfromidBarra(k);

            for (int i = 0; i < dof; ++i) {//no estoy seguro de q si ese 3 es = a dof.. preguntar
                for (int h = 0; h < dof; ++h) {//ideam anterior
                    int fila= ni * dof + i-dof;
                    int col=ni * dof + h-dof;
                    s_MS[fila][col] += bar.getSm(i, h);
                    s_MS[fila][col] += bar.getSm(i, h + dof);
                    s_MS[fila][col] += bar.getSm(i + dof, h);
                    s_MS[fila][col] += bar.getSm(i + dof, h + dof);
                }
            }
        }
        return s_MS;
    }

    public double[][] crear_Sms(){
        this.makeS_Ms();
        for (int k = 0; k < cantBarras; ++k) {
            Conectividad con = getFromIdBarraConectividad(k+1);
            int ni = con.getNumNudoInicial();
            int nf = con.getNumNudoFinal();
            Barra bar = getfromidBarra(k+1);

            for (int i = 0; i < dof; ++i) {//no estoy seguro de q si ese 3 es = a dof.. preguntar
                for (int h = 0; h < dof; ++h) {//ideam anterior
                    int fila= ni * dof + i-dof;
                    int col=ni * dof + h-dof;
                    s_MS[fila][col] += bar.getSm(i, h);
                    s_MS[fila][col+dof] += bar.getSm(i, h + dof);
                    s_MS[fila+dof][col] += bar.getSm(i + dof, h);
                    s_MS[fila+dof][col+dof] += bar.getSm(i + dof, h + dof);
                }
            }
        }
        return s_MS;
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

    public double [][] makeSFF(){

        int[] restG= makeKRestGlob();
        double [][] kred = new double[nk][nk];

        for (int i = 0; i < dof * cantNodos; ++i) {
            if (restG[i] != -1) {
                for (int j = 0; j < dof * cantNodos; ++j) {
                    if (restG[j] != -1) {
                        kred[restG[i]][restG[j]] = getS_MS()[i][j];
                    }
                }
            }
        }
        return kred;
    }

    public double[] makeAF(){//No se como toma las fuerzas
        nk=0;
        int[] restG= makeKRestGlob();
        double [] pred = new double[nk];
//        for (int i = 0; i < dof * cantNodos; ++i) {
//            if (restG[i] != -1) {
//                int idj = i / 3;
//                int idf = i % 3;
//                //pred[makeKRestGlob()[i]] = getfromidNudo(idj).getF()[idf];
//                pred[restG[i]] = getFromIdNudoCarga(idj+1).getCargaEnX();
//            }
//        }
        return pred;
    }

    @Override
    public String toString() {
        return "Estructura{" + "listaBarras=" + listaBarras + ", listaNudos=" + listaNudos + '}';
    }
}
