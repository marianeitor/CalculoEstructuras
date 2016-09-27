package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class CargaEnBarra implements Serializable{
    private int numBarra;
    private double cargaDistribuida;
    private double cargaPuntualDistXY;
    private double cargaPuntualDistZ;
    private double cargaPuntualEnX;
    private double cargaPuntualEnY;
    private double cargaPuntualEnZ;

    public CargaEnBarra(int numBarra) {
        this.numBarra = numBarra;
    }

    public CargaEnBarra(int numBarra,  double cargaX, double cargaY, double cargaZ, double distancia, double cargaDist) {
        this.numBarra = numBarra;
        this.cargaDistribuida = cargaDist;
        this.cargaPuntualDistXY = distancia;
        this.cargaPuntualEnX = cargaX;
        this.cargaPuntualEnY = cargaY;
        this.cargaPuntualEnZ = cargaZ;
    }

    public void setNumBarra(int numBarra) {
        this.numBarra = numBarra;
    }

    public void setCargaDistribuida(double cargaDirstribuida) {
        this.cargaDistribuida = cargaDirstribuida;
    }

    public void setCargaPuntualDistXY(double cargaPuntualDistXY) {
        this.cargaPuntualDistXY = cargaPuntualDistXY;
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

    public double getCargaPuntualDistZ() {
        return cargaPuntualDistZ;
    }

    public void setCargaPuntualDistZ(double cargaPuntualDistZ) {
        this.cargaPuntualDistZ = cargaPuntualDistZ;
    }

    public int getNumBarra() {
        return numBarra;
    }

    public double getCargaDistribuida() {
        return cargaDistribuida;
    }

    public double getCargaPuntualDistXY() {
        return cargaPuntualDistXY;
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
