package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class Nudo implements Serializable{
    int nOrden;
    double x;
    double y;
    boolean restriccionX;
    boolean restriccionY;
    boolean restriccionGiro;// reastricciones x, y , giro
    double fuerzaX;
    double fuerzaY;
    double momento; //fuerzas x,y, momento

    public boolean isRestriccionX() {
        return restriccionX;
    }

    public double getFuerzaY() {
        return fuerzaY;
    }

    public boolean isRestriccionY() {
        return restriccionY;
    }

    public boolean isRestriccionGiro() {
        return restriccionGiro;
    }

    public void setFuerzaY(double fuerzaY) {
        this.fuerzaY = fuerzaY;
    }

    public double getMomento() {
        return momento;
    }

    public void setMomento(double momento) {
        this.momento = momento;
    }

    public void setRestricciones(boolean ddx, boolean ddy, boolean ddr) {
        this.restriccionX = ddx;
        this.restriccionY = ddy;
        this.restriccionGiro = ddr;
    }

    public double getFuerzaX() {
        return fuerzaX;
    }

    public void setFuerzaX(double f ) {
        this.fuerzaX = f;
    }

    public Nudo(int nOrden, double x, double y) {
        this.nOrden = nOrden;
        this.x = x;
        this.y = y;
        //this.restriccionX = false;
       // this.restriccionY = false;
      //  this.restriccionGiro = false;
       // this.fuerzaX = 0;
       // this.fuerzaY = 0;
       // this.momento = 0;
    }

    public Nudo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getnOrden() {
        return nOrden;
    }

    public void setnOrden(int nOrden) {
        this.nOrden = nOrden;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
