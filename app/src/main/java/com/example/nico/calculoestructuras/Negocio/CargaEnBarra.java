package com.example.nico.calculoestructuras.Negocio;

/**
 * Created by Nico on 9/11/2015.
 */
public class CargaEnBarra {
    private int numBarra;
    private double cargaDirstribuida;
    private double cargaPuntualDistANudo;
    private double cargaPuntualEnX;
    private double cargaPuntualEnY;
    private double cargaPuntualEnZ;

    public CargaEnBarra(int numBarra) {
        this.numBarra = numBarra;
    }

    public void setNumBarra(int numBarra) {
        this.numBarra = numBarra;
    }

    public void setCargaDirstribuida(double cargaDirstribuida) {
        this.cargaDirstribuida = cargaDirstribuida;
    }

    public void setCargaPuntualDistANudo(double cargaPuntualDistANudo) {
        this.cargaPuntualDistANudo = cargaPuntualDistANudo;
    }

    public void setCargaPuntualEnX(double cargaPuntualEnX) {
        this.cargaPuntualEnX = cargaPuntualEnX;
    }

    public void setCargaPuntualEnY(double cargaPuntualEnY) {
        this.cargaPuntualEnY = cargaPuntualEnY;
    }

    public void setCargaPuntualEnZ(double cargaPuntualEnZ) {
        this.cargaPuntualEnZ = cargaPuntualEnZ;
    }

    public int getNumBarra() {
        return numBarra;
    }

    public double getCargaDirstribuida() {
        return cargaDirstribuida;
    }

    public double getCargaPuntualDistANudo() {
        return cargaPuntualDistANudo;
    }

    public double getCargaPuntualEnX() {
        return cargaPuntualEnX;
    }

    public double getCargaPuntualEnY() {
        return cargaPuntualEnY;
    }

    public double getCargaPuntualEnZ() {
        return cargaPuntualEnZ;
    }
}
