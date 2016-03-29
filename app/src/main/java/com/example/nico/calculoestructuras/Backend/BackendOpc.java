package com.example.nico.calculoestructuras.Backend;

import java.util.ArrayList;

/**
 * Created by Nico on 25/11/2015.
 */
public class BackendOpc extends Object{
    private static BackendOpc instance;
    private ArrayList<String> lista;
    //constructor privado
    private BackendOpc()
    {
        lista = new ArrayList<>();
    }
    public static BackendOpc getInstance()
    {
        instance = new BackendOpc();
        return instance;
    }

    public ArrayList<String> getStringList()
    {

        lista.add(" NUDOS ");
        lista.add(" BARRAS ");
        lista.add(" CONECTIVIDADES ");
        lista.add(" VINCULOS ");
        lista.add(" CARGAS ");
        lista.add(" ANALIZAR ");
        lista.add(" MATRICES RIGIDEZ ");

        return lista;

    }
}
