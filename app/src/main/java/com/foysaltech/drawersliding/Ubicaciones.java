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
    private String calle1;
    private String referencia;

    public void setNro_casa(String nro_casa) {
        this.nro_casa = nro_casa;
    }

    private String nro_casa;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCalle2() {
        return calle2;
    }

    public String getNro_casa() {
        return nro_casa;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    private String calle2;

    private Double latitud;
    private Double longitud;


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
