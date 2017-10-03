package com.example.nico.calculoestructuras.Negocio;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Nico on 9/11/2015.
 */
public class PorticoPlano2 extends Estructura2 implements Serializable{
    private static final int dof = 3;

    public PorticoPlano2(Context context) {
        super(dof,context);
    }
}
