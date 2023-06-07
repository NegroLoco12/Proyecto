package com.foysaltech.drawersliding;

import java.util.ArrayList;

public class Carritos {
    public static final ArrayList<Pedidos> pedido = new ArrayList<>();

    public static String agregarPedidos(Pedidos pedidos) {
        int index = 0;
        boolean b = false;
        for (Pedidos dp : pedido) {
            if (dp.getCodigo() == pedidos.getCodigo()) {
                pedido.set(index, pedidos);
                b = true;
              //  return "El platillo ha sido agregado al carrito, se actualizará la cantidad";
            }
            index++;
        }
        if (!b) {
            pedido.add(pedidos);
        }
        return ". . . . ";
    }
    public static int getPrecioDefinitivo() {
        int suma=0;
        for (int i = 0; i < pedido.size(); i++) {
            suma =suma+pedido.get(i).getPrecio_total();
        }
        return suma;

    }
}
