package com.foysaltech.drawersliding;

public class Ubicaciones {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    String key;
    String nombre;
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

}
