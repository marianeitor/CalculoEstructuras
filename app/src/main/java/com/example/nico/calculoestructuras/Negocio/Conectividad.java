package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class Conectividad implements Serializable {
    int numBarra;
    int numNudoInicial;
    int numNudoFinal;

    public Conectividad(int numBarra, int numNudoInicial, int numNudoFinal) {
        this.numBarra = numBarra;
        this.numNudoInicial = numNudoInicial;
        this.numNudoFinal = numNudoFinal;
    }

    public int getNumBarra() {
        return numBarra;
    }

    public void setNumBarra(int numBarra) {
        this.numBarra = numBarra;
    }

    public int getNumNudoInicial() {
        return numNudoInicial;
    }

    public void setNumNudoInicial(int numNudoInicial) {
        this.numNudoInicial = numNudoInicial;
    }

    public int getNumNudoFinal() {
        return numNudoFinal;
    }

    public void setNumNudoFinal(int numNudoFinal) {
        this.numNudoFinal = numNudoFinal;
    }

    @Override
    public String toString() {
        return "Conectividad{" +
                "numBarra=" + numBarra +
                ", numNudoInicial=" + numNudoInicial +
                ", numNudoFinal=" + numNudoFinal +
                '}';
    }
}
