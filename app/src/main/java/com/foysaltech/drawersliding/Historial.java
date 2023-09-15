package com.foysaltech.drawersliding;

public class Historial {
  private  String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    private String fecha;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    private int estado;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    private String hora;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getClave_pk() {
        return clave_pk;
    }

    public void setClave_pk(String clave_pk) {
        this.clave_pk = clave_pk;
    }

    private String clave_pk;
}
