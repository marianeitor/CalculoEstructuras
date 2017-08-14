package com.example.nico.calculoestructuras.Negocio;

import java.util.ArrayList;

/**
 * Created by Nico on 9/11/2015.
 */
public class Estructura {
    private Barra[] arrayBarras;
    private Nudo[] arrayNodos;
    private Conectividad[] arrayConectividades;
    private Vinculo[] arrayVinculo;
    private CargaEnNudo[] arrayCargaEnNudo;
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

    public Estructura(int n, int e, int d) {
        cantNodos=n;
        cantBarras=e;
        dof=d;
        arrayNodos = new Nudo[n];
        arrayBarras = new Barra[e];
        arrayConectividades = new Conectividad[e];
        arrayVinculo= new Vinculo[n];
        arrayCargaEnNudo= new CargaEnNudo[n];
        for (int i = 0; i < n; i++) {
            arrayNodos[i] = new Nudo(0, 0,0);
            arrayVinculo[i]= new Vinculo(0);
            arrayCargaEnNudo[i]= new CargaEnNudo(0);
        }
        for (int j = 0; j < e; j++) {
            arrayBarras[j] = new Barra(0, 0.0, 0.0, 0.0);
            arrayBarras[j].construct(0,0,0);
            arrayConectividades[j] = new Conectividad(0,0,0);
        }
    }

    public double[][] getS_MS() {
        return s_MS;
    }

    public Barra[] getArrayBarras() {
        return arrayBarras;
    }

    public void setArrayBarras(Barra[] arrayBarras) {
        this.arrayBarras = arrayBarras;
    }

    public Nudo[] getArrayNodos() {
        return arrayNodos;
    }

    public void setArrayNodos(Nudo[] arrayNodos) {
        this.arrayNodos = arrayNodos;
    }

    public void crearNodo(int i, double x, double y, boolean f1, boolean f2, boolean f3) {
        arrayNodos[i] = new Nudo(i,x, y);
        arrayNodos[i].setRestricciones(f1,f2,f3);
    }

    public void setFNodo(int i, double[] f) {
        arrayNodos[i].setFuerzaX(f[0]);
        arrayNodos[i].setFuerzaX(f[1]);
        arrayNodos[i].setFuerzaX(f[2]);
    }

    public void crearBarra(int i, int nodoi, int nodof, double elasticidad, double area, double inercia) {
        Nudo ni = getfromidNudo(nodoi - 1);
        Nudo nf = getfromidNudo(nodof - 1);
        double len = Math.sqrt((ni.getX() - nf.getX()) * (ni.getX() - nf.getX()) + (ni.getY() - nf.getY()) * (ni.getY() - nf.getY()));
        double g1 = (nf.getX() - ni.getX()) / len; // cosenos directores
        double g2 = (nf.getY() - ni.getY()) / len;
        arrayBarras[i] = new Barra(i, elasticidad, area, inercia);
        arrayBarras[i].construct(len, g1, g2);

    }
    public void crearConectividad(int barra,int ni, int nf){
        arrayConectividades[barra-1]= new Conectividad(barra,ni,nf);
    }
    public void crearVinculo(int nudo, double restx, double resty, double restgiro){// No toma el valor de las restricriones
        this.arrayVinculo[nudo-1]= new Vinculo(nudo);
        if(restgiro!=0) {
            arrayVinculo[nudo - 1].setRestGiro(restgiro);
        }
        if(restx!=0){
            arrayVinculo[nudo-1].setRestX(restx);
        }
        if(resty!=0){
            arrayVinculo[nudo-1].setRestY(resty);
        }
    }
    public void crearCargaNodo(int nudo, double cargax, double cargay, double cargaz){
        arrayCargaEnNudo[nudo-1]= new CargaEnNudo(nudo);
        if(cargax!=0) {
            arrayCargaEnNudo[nudo - 1].setCargaEnX(cargax);
        }
        if(cargay!=0){
            arrayCargaEnNudo[nudo-1].setCargaEnY(cargay);
        }
        if(cargaz!=0){
            arrayCargaEnNudo[nudo-1].setCargaEnZ(cargaz);
        }

    }

    public void makeS_Ms() {
        s_MS = new double[dof * cantNodos][dof * cantNodos];
        for (int i = 0; i < dof * cantNodos; ++i) {
            for (int h = 0; h < dof * cantNodos; ++h) {
                s_MS[i][h] = 0;
            }
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
                    s_MS[fila][col] += bar.getElement(i, h);
                    s_MS[fila][col] += bar.getElement(i, h + dof);
                    s_MS[fila][col] += bar.getElement(i + dof, h);
                    s_MS[fila][col] += bar.getElement(i + dof, h + dof);
                }
            }
        }
        return s_MS;
    }

    public double[][] crear_Sms(){
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
                    s_MS[fila][col] += bar.getElement(i, h);
                    s_MS[fila][col+dof] += bar.getElement(i, h + dof);
                    s_MS[fila+dof][col] += bar.getElement(i + dof, h);
                    s_MS[fila+dof][col+dof] += bar.getElement(i + dof, h + dof);
                }
            }
        }
        return s_MS;
    }




    public void allocNodo(int n) {
        arrayNodos = new Nudo[n];
    }

    public Barra getfromidBarra(int i) {
        if(arrayBarras.length > 0 && arrayBarras.length> i){
            return arrayBarras[i];}
        return null;
    }
    public Conectividad getFromIdBarraConectividad(int i){
        if(arrayConectividades.length>0&& arrayConectividades.length>i){
            return arrayConectividades[i];
        }
        return null;
    }
    public Vinculo getFromIdNudoVinculo(int i){
        if(arrayVinculo.length>0&&arrayVinculo.length>i){
            return arrayVinculo[i];
        }
        return null;
    }

    public Nudo getfromidNudo(int i) {
        return arrayNodos[i];
    }
    public CargaEnNudo getFromIdNudoCarga(int i){
        if(arrayCargaEnNudo.length>0&&arrayCargaEnNudo.length>i) {
            return arrayCargaEnNudo[i];
        }
        return null;
    }
    public int[] makeKRestGlob(){
        int[] restglob = new int[dof * cantNodos];
        for (int i = 0; i < cantNodos; ++i) {
            Vinculo vin= getFromIdNudoVinculo(i);
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
        for (int i = 0; i < dof * cantNodos; ++i) {
            if (restG[i] != -1) {
                int idj = i / 3;
                int idf = i % 3;
                //pred[makeKRestGlob()[i]] = getfromidNudo(idj).getF()[idf];
                pred[restG[i]] = getFromIdNudoCarga(idj).getCargaEnX();
            }
        }
        return pred;
    }

    @Override
    public String toString() {
        return "Estructura{" + "arrayBarras=" + arrayBarras + ", arrayNodos=" + arrayNodos + '}';
    }
}
