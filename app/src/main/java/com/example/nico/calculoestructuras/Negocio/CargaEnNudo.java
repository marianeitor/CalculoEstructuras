package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class CargaEnNudo implements Serializable{
    private int numNudo;
    private double cargaEnX = 0;
    private double cargaEnY = 0;
    private double cargaEnZ = 0;

    public CargaEnNudo(int numNudo) {
        this.numNudo = numNudo;
    }

    public int getNumNudo() {
        return numNudo;
    }

    public void setNumNudo(int numNudo) {
        this.numNudo = numNudo;
    }

    public double getCargaEnX() {
        return cargaEnX;
    }

    public void setCargaEnX(double cargaEnX) {
        this.cargaEnX = cargaEnX;
    }

    public double getCargaEnY() {
        return cargaEnY;
    }

    public void setCargaEnY(double cargaEnY) {
        this.cargaEnY = cargaEnY;
    }

    public double getCargaEnZ() {
        return cargaEnZ;
    }

    public void setCargaEnZ(double cargaEnZ) {
        this.cargaEnZ = cargaEnZ;
    }
}
