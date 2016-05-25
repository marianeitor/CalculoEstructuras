package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class Vinculo implements Serializable{
    private int numNudo;
    private double restX=0;
    private double restY=0;
    private double restGiro=0;

    public Vinculo(int numNudo) {
        this.numNudo = numNudo;
    }

    public int getNumNudo() {
        return numNudo;
    }

    public void setNumNudo(int numNudo) {
        this.numNudo = numNudo;
    }

    public double getRestX() {
        return restX;
    }

    public void setRestX(double restX) {
        this.restX = restX;
    }

    public double getRestY() {
        return restY;
    }

    public void setRestY(double restY) {
        this.restY = restY;
    }

    public double getRestGiro() {
        return restGiro;
    }

    public void setRestGiro(double restGiro) {
        this.restGiro = restGiro;
    }
}
