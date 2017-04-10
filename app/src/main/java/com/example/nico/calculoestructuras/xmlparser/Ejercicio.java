package com.example.nico.calculoestructuras.xmlparser;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;

import java.util.ArrayList;

/**
 * Created by Carlos on May 2016.
 */

public class Ejercicio {
    public ArrayList<Nudo> nudos;
    public ArrayList<Barra> barras;
    public ArrayList<Conectividad> conectividades;
    public ArrayList<Vinculo> vinculos;
    public ArrayList<CargaEnNudo> cargaNudo;
    public ArrayList<CargaEnBarra> cargaBarra;

    public Ejercicio(ArrayList<Nudo> nudos, ArrayList<Barra> barras, ArrayList<Conectividad> conectividades,
                     ArrayList<Vinculo> vinculos, ArrayList<CargaEnNudo> cargaNudo, ArrayList<CargaEnBarra> cargaBarra) {
        this.nudos = nudos;
        this.barras = barras;
        this.conectividades = conectividades;
        this.vinculos = vinculos;
        this.cargaNudo = cargaNudo;
        this.cargaBarra = cargaBarra;
    }
}
