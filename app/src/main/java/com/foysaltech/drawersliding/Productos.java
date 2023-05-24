package com.foysaltech.drawersliding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Base64;

public class Productos {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    String nombre;
    String descripcion;
    String precio;

    public Boolean getEstado_promo() {
        return estado_promo;
    }

    public void setEstado_promo(Boolean estado_promo) {
        this.estado_promo = estado_promo;
    }

    Boolean estado_promo;

    public String getPrecio_promo() {
        return precio_promo;
    }

    public void setPrecio_promo(String precio_promo) {
        this.precio_promo = precio_promo;
    }

    String precio_promo;
    int codigo;
    Bitmap imagen2;
    String imagen;

    String dato;
    public Bitmap getImagen2() {
        byte[] byteCode=   Base64.getDecoder().decode(this.imagen);
        imagen2= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
        return imagen2;
    }

    public void setImagen2(Bitmap imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }




    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }





    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}

