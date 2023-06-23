package com.foysaltech.drawersliding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Base64;

public class Categorias {
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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

    String descripcion;
    Bitmap imagen2;
    String imagen;






    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

}

