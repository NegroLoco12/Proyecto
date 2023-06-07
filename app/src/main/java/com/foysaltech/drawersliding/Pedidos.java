package com.foysaltech.drawersliding;

import android.graphics.Bitmap;

public class Pedidos {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String nombre;
    String codigo;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    int cantidad;

    public int getPrecio_inicial() {
        return precio_inicial;
    }

    public void setPrecio_inicial(int precio_inicial) {
        this.precio_inicial = precio_inicial;
    }

    int precio_inicial;

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    Bitmap imagen;

    public int getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(int precio_total) {
        this.precio_total = precio_total;
    }

    int precio_total;
}
