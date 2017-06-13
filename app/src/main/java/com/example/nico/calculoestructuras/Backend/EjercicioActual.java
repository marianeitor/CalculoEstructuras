package com.example.nico.calculoestructuras.Backend;

import android.app.Application;

/**
 * Created by Carlos on May 2016.
 */

public class EjercicioActual extends Application {

    private String nombreEjercicio = null;

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

}
