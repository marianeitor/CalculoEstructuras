package com.example.nico.calculoestructuras.Ejercicios;

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
public class Ejercicio1 {

    public ArrayList<Nudo> arrayNudos = new ArrayList<Nudo>(){{
        add(new Nudo(0.0, 0.0));
        add(new Nudo(3.0, 2.0));
        add(new Nudo(3.0, 0.0));
    }};

    public ArrayList<Barra> arrayBarras = new ArrayList<Barra>(){{
        add(new Barra(1, 2100000.0, 5.0, 600.0));
        add(new Barra(2, 2100000.0, 4.0, 400.0));
    }};

    public ArrayList<Conectividad> arrayConectividades = new ArrayList<Conectividad>(){{
        add(new Conectividad(1, 1, 3));
        add(new Conectividad(2, 2, 3));
    }};

    public ArrayList<Vinculo> arrayVinculos = new ArrayList<Vinculo>(){{
        add(new Vinculo(1));
        add(new Vinculo(2));
        add(new Vinculo(3));
    }};

    public ArrayList<CargaEnNudo> arrayCargasEnNudos = new ArrayList<CargaEnNudo>(){{
        add(new CargaEnNudo(1, 0.0, 0.0, 0.0));
        add(new CargaEnNudo(2, 0.0, 0.0, 0.0));
        add(new CargaEnNudo(3, 0.0, 0.0, 0.0));
    }};

    public ArrayList<CargaEnBarra> arrayCargasEnBarras = new ArrayList<CargaEnBarra>(){{
        add(new CargaEnBarra(1, 0.0, -200.0, 0.0, 1.5, 0));
        add(new CargaEnBarra(2, 0.0, 0.0, 0.0, 0, 50.0));
    }};

}
