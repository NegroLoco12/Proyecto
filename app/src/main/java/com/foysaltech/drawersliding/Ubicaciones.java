package com.foysaltech.drawersliding;

import java.util.ArrayList;

public class Ubicaciones {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    String key;

    public String getNombre_direccion() {
        return nombre_direccion;
    }

    public void setNombre_direccion(String nombre_direccion) {
        this.nombre_direccion = nombre_direccion;
    }

    String nombre_direccion;
    String calle1;


    public Double getLatitudActual() {
        return latitudActual;
    }

    public void setLatitudActual(Double latitudActual) {
        this.latitudActual = latitudActual;
    }

    public Double getLongitulActual() {
        return longitulActual;
    }

    public void setLongitulActual(Double longitulActual) {
        this.longitulActual = longitulActual;
    }

    private Double latitudActual;
    private Double longitulActual;
    public static final ArrayList<Ubicaciones> guardado = new ArrayList<>();
    public static void guardarUbi(Ubicaciones coordenadas) {
        guardado.add(coordenadas);
    }
    public static void eliminarUbi() {
        guardado.clear();
    }
}
