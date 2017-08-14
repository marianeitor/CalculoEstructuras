package com.example.nico.calculoestructuras.Negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Nico on 9/11/2015.
 */
public class Barra implements Serializable {
    int numOrden=0;
    Double elasticidad;
    Double area;
    Double inercia;
    double longitud;
    double g1; //coseno director
    double g2; //coseno director
    double[][] matrizElemental;

    public Barra() {} // Constructor especial para armar barras desde un archivo xml

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getG1() {
        return g1;
    }

    public void setG1(double g1) {
        this.g1 = g1;
    }

    public double getG2() {
        return g2;
    }

    public void setG2(double g2) {
        this.g2 = g2;
    }

    /**
     * Obtiene el elemento de la matriz elemental en la ubicación dada.
     * @param fila    fila
     * @param columna columna
     * @return double
     */
    public double getElement(int fila, int columna) {
        return matrizElemental[fila][columna];
    }

    /**
     * Devuelve la matriz elemental.
     * @return double[][]
     */
    public double[][] getMatrizElemental() {
        return matrizElemental;
    }


    public void setMatrizElemental(double[][] matrizElemental) {
        this.matrizElemental = matrizElemental;
    }


    public Barra(Double elasticidad, Double area, Double inercia) {
        this.elasticidad = elasticidad;
        this.area = area;
        this.inercia = inercia;
    }

    public Barra(int numOrden, Double elasticidad, Double area, Double inercia) {
        this.elasticidad = elasticidad;
        this.area = area;
        this.inercia = inercia;
        this.numOrden = numOrden;
    }
    public void construct (double longitud,double g, double h ){
        this.longitud=longitud;
        this.g1=g;
        this.g2=h;
        double s = elasticidad * area / longitud;
        double s1 = 12 * elasticidad * inercia / (longitud * longitud * longitud);
        double s2 = 6 * elasticidad * inercia / (longitud * longitud);
        double s3 = 4 * elasticidad * inercia / longitud;
        double a = g1 * g1 * s + g2 * g2 * s1;
        double b = g1 * g2 * (s - s1);
        double c = g2 * s2;
        double d = g2 * g2 * s + g1 * g1 * s1;
        double e = g1 * s2;

        matrizElemental = new double[][]{
                {a, b, -c, -a, -b, -c},
                {b, d, e, -b, -d, e},
                {-c, e, s3, c, -e, s3 / 2},
                {-a, -b, c, a, b, c},
                {-b, -d, -e, b, d, -e},
                {-c, e, s3 / 2, c, -e, s3}
        };
    }

    @Override
    public String toString() {
        String res="";

        for (int i=0;i<6;i++) {
            res+=" [ ";
            for (int j=0;j<6;j++) {
                double d = getElement(i, j);
                BigDecimal big = new BigDecimal(d);
                big = big.setScale(2, RoundingMode.HALF_UP);
                res+=big;
                if(j!=5){
                    res +=" , ";}
                else res +=" ] "+"\n";
            }
            res+="\n";
        }
        //res+="\n"+" ";
        return res;
    }

    public Double getElasticidad() {
        return elasticidad;
    }

    public void setElasticidad(Double elasticidad) {
        this.elasticidad = elasticidad;
    }

    public Double getArea() {
        return area;
    }

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getInercia() {
        return inercia;
    }

    public void setInercia(Double inercia) {
        this.inercia = inercia;
    }
}
