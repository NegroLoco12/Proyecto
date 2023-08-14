package com.foysaltech.drawersliding;

public class productos_historial {
    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    private int cantidad_producto;
    private String cod_producto;

    public Integer getSubTotal_producto() {
        return subTotal_producto;
    }

    public void setSubTotal_producto(Integer subTotal_producto) {
        this.subTotal_producto = subTotal_producto;
    }

    private Integer subTotal_producto;

}
