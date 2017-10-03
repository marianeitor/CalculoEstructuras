package com.example.nico.calculoestructuras.Negocio;

import android.content.Context;

import com.example.nico.calculoestructuras.DataBase.DataBaseHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Nico on 9/11/2015.
 */
public class Estructura2 implements Serializable{
    private final int dof;
    private int cantidadElementosRestringidos;
    private int cantidadElementosLibres;
    private final int cantidadBarras;
    private final int cantidadNodos;
    private double[][] matrizGlobal;
    private double[][] matrizGlobalOrdenada;
    private double[][] matrizSFF;
    private double[][] matrizSFR;
    private double[][] matrizSRF;
    private double[][] matrizSRR;
    private boolean[] tablaRestricciones;
    private ArrayList<String> matricesElementales;
    private int nk = 0;
    ArrayList<Barra> listaBarras;
    private ArrayList<Nudo> listaNudos;
    private ArrayList<Conectividad> listaConectividades;
    private ArrayList<Vinculo> listaVinculos;
    private ArrayList<CargaEnBarra> listaCargasEnBarras;
    private ArrayList<CargaEnNudo> listasCargasEnNudos;

    /**
     * Constructor de Estructura2
     * @param dof        dof
     * @param context    Context
     */
    public Estructura2(int dof, Context context) {
        cantidadElementosLibres = 0;
        cantidadElementosRestringidos = 0;
        listaBarras = DataBaseHelper.getDatabaseInstance(context).getBarrasFromDB();
        listaNudos = DataBaseHelper.getDatabaseInstance(context).getNudosFromDB();
        listaConectividades = DataBaseHelper.getDatabaseInstance(context).getConecFromDB();
        listaVinculos = DataBaseHelper.getDatabaseInstance(context).getVinculoFromDB();
        listaCargasEnBarras = DataBaseHelper.getDatabaseInstance(context).getCargaEnBarraFromDB();
        listasCargasEnNudos = DataBaseHelper.getDatabaseInstance(context).getCargaEnNudoFromDB();
        cantidadNodos = listaNudos.size();
        cantidadBarras = listaBarras.size();
        this.dof = dof;
        this.calcularBarras();
        this.cargarMatricesElementales();
        this.crearMatrizGlobal();
        this.crearMatrizGlobalOrdenada();
        this.crearMatricesRestantes();
    }

    /**
     * Cada getX() devuelve la matriz requerida.
     */
    public double[][] getMatrizGlobal() {
        return matrizGlobal;
    }

    public double[][] getMatrizSFF() {
        return matrizSFF;
    }

    public double[][] getMatrizSFR() {
        return matrizSFR;
    }

    public double[][] getMatrizSRF() {
        return matrizSRF;
    }

    public double[][] getMatrizSRR() {
        return matrizSRR;
    }

    /**
     * Devuelve un string con los elementos de la matriz fuente.
     * @param matriz Matriz fuente
     * @return String
     */
    public String getMatrizToString(double[][] matriz){
        String str="";
        for (double[] aMatrizGlobal : matriz) {
            str += " [ ";
            for (int j = 0; j < aMatrizGlobal.length; j++) {
                double d = aMatrizGlobal[j];
                BigDecimal big = new BigDecimal(d);
                big = big.setScale(2, RoundingMode.HALF_UP);
                str += big;
                if (j != aMatrizGlobal.length - 1) {
                    str += " , ";
                } else {
                    str += " ] " + "\n";
                }
            }
            str += "\n";
        }
        return str;
    }

    /**
     * Toma cada barra y le construye la matriz elemental.
     */
    private void calcularBarras() {
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
     * Reune las matrices elementales de cada barra en una variable general
     */
    private void cargarMatricesElementales(){
        if(listaBarras.size() != 0) {
            matricesElementales = new ArrayList<>();
            for (int i = 1; i <= listaBarras.size(); i++) {
                matricesElementales.add(getfromidBarra(i).toString());
            }
        }
    }

    /**
     * Obtiene las matrices elementales
     * @return double[][] | null
     */
    public ArrayList<String> getMatricesElementales(){
        if(matricesElementales != null){
            return matricesElementales;}
        return null;
    }

    /**
     * Forma la matriz global a partir de las matrices elementales.
     */
    private void crearMatrizGlobal(){
        matrizGlobal = new double[this.dof * cantidadNodos][this.dof * cantidadNodos];
        for (int k = 0; k < cantidadBarras; ++k) {
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
    }

    /**
     * Copia un cuadrante en su posición dentro de la matriz global.
     * Cuadrante: la matriz elemental de cada barra puede dividirse en 4 cuadrantes, donde cada uno es una
     * submatriz de 3x3.
     * @param nudoFila    Id del nudo correspondiente a las filas del cuadrante.
     * @param nudoColumna Id del nudo correspondiente a las columnas del cuadrante.
     * @param cuadrante   Cuadrante propiamente dicho, array de elementos.
     */
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
     * A partir de la matrizGlobal carga la matrizGlobalOrdenada según las restricciones.
     */
    private void crearMatrizGlobalOrdenada(){
        matrizGlobalOrdenada = new double[this.dof * cantidadNodos][this.dof * cantidadNodos];
        this.crearTablaRestricciones();
        this.ordenarFilas();
        this.ordenarColumnas();
    }

    /**
     * Crea una tabla con todos los ejes de cada nudo y sus restricciones.
     */
    private void crearTablaRestricciones(){
        tablaRestricciones = new boolean[dof * cantidadNodos];
        int posicion = 0;
        for (Nudo nudo:listaNudos) {
            Vinculo vinculo = getFromIdNudoVinculo(nudo.getnOrden());
            tablaRestricciones[posicion] = !Double.isNaN(vinculo.getRestX());
            tablaRestricciones[posicion + 1] = !Double.isNaN(vinculo.getRestY());
            tablaRestricciones[posicion + 2] = !Double.isNaN(vinculo.getRestGiro());
            posicion += dof;
        }
        for (boolean elemento:tablaRestricciones) {
            if (elemento) {
                cantidadElementosRestringidos ++;
            } else {
                cantidadElementosLibres ++;
            }
        }
    }

    /**
     * Ordena las filas de la matriz global poniendo dentro de la matriz gobal ordenada aquellas
     * que no están restringidas en las primeras posiciones y las restringidas al último.
     */
    private void ordenarFilas() {
        int index = 0;
        // Agrega todas las filas que no están restringidas primero
        for (int i = 0 ; i < tablaRestricciones.length ; i++) {
            if (!tablaRestricciones[i]) {
                matrizGlobalOrdenada[index] = matrizGlobal[i];
                index ++;
            }
        }
        // Luego las que sí estan restringidas
        for (int i = 0 ; i < tablaRestricciones.length ; i++) {
            if (tablaRestricciones[i]) {
                matrizGlobalOrdenada[index] = matrizGlobal[i];
                index ++;
            }
        }
    }

    /**
     * Ordena las columnas o elementos de cada fila poniendo aquellos que están libres primero
     * y luego los que están restringidos. Para ello crea un vector y pone los elementos ordenados
     * y después copia el contenido de ese vector ordenado en la fila desordenada.
     */
    private void ordenarColumnas() {
        // Hace lo mismo que ordenar filas pero con los elementos de cada fila
        for (int fila = 0; fila < matrizGlobalOrdenada.length; fila++) {
            Vector<Double> filaOrdenada = new Vector<>();
            // Agrega todos los elementos que no están restringidos primero
            for (int i = 0 ; i < tablaRestricciones.length ; i++) {
                if (!tablaRestricciones[i]) {
                    filaOrdenada.add(matrizGlobalOrdenada[fila][i]);
                }
            }
            // Luego los que sí estan restringidos
            for (int i = 0 ; i < tablaRestricciones.length ; i++) {
                if (tablaRestricciones[i]) {
                    filaOrdenada.add(matrizGlobalOrdenada[fila][i]);
                }
            }
            // Por ultimo copia en la fila los elementos del vector ordenado
            for (int i = 0 ; i < filaOrdenada.size() ; i++) {
                matrizGlobalOrdenada[fila][i] = filaOrdenada.get(i);
            }
        }
    }

    /**
     * Crea las matrices SFF, SFR, SRF y SRR según la matriz global ordenada.
     */
    private void crearMatricesRestantes() {
        matrizSFF = new double[cantidadElementosLibres][cantidadElementosLibres];
        matrizSFR = new double[cantidadElementosLibres][cantidadElementosRestringidos];
        matrizSRF = new double[cantidadElementosRestringidos][cantidadElementosLibres];
        matrizSRR = new double[cantidadElementosRestringidos][cantidadElementosRestringidos];

        // Según si el elemento está en la fila o columna restringida o libre, lo acomoda en la
        // matriz correspondiente
        for (int fila = 0; fila < matrizGlobalOrdenada.length; fila++) {
            for (int columna = 0; columna < matrizGlobalOrdenada[fila].length; columna++) {
                if (fila < cantidadElementosLibres) {
                    if (columna < cantidadElementosLibres) {
                        matrizSFF[fila][columna] = matrizGlobalOrdenada[fila][columna];
                    } else {
                        matrizSFR[fila][columna-cantidadElementosLibres] = matrizGlobalOrdenada[fila][columna];
                    }
                } else {
                    if (columna < cantidadElementosLibres) {
                        matrizSRF[fila-cantidadElementosLibres][columna] = matrizGlobalOrdenada[fila][columna];
                    } else {
                        matrizSRR[fila-cantidadElementosLibres][columna-cantidadElementosLibres] = matrizGlobalOrdenada[fila][columna];
                    }
                }
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
        Vinculo vinculoEncontrado = null;
        for (Vinculo vinculo:listaVinculos) {
            if(vinculo.getNumNudo() == id)
                vinculoEncontrado = vinculo;
        }
        return vinculoEncontrado;
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

    @Override
    public String toString() {
        return "Estructura{" + "listaBarras=" + listaBarras + ", listaNudos=" + listaNudos + '}';
    }
}
