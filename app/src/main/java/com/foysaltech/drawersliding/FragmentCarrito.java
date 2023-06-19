package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class FragmentCarrito extends Fragment {
RecyclerView contenedorPedidos;
TextView txt_sub_total_carrito,txt_descuento_carrito,txt_total_carrito;
String precio_final;
    public FragmentCarrito() {

    }


    public static FragmentCarrito newInstance(String param1, String param2) {
        FragmentCarrito fragment = new FragmentCarrito();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_carrito, container, false);
        contenedorPedidos=view.findViewById(R.id.contenedorCarrito);
        txt_sub_total_carrito=view.findViewById(R.id.txt_sub_total_carrito);
        txt_descuento_carrito=view.findViewById(R.id.txt_descuento_carrito);
        txt_total_carrito=view.findViewById(R.id.txt_total_carrito);
        view.findViewById(R.id.borrar_todo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar_carrito();
            }
        });

        view.findViewById(R.id.btnCarrito).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedScreen = new FragmentDetallePedido();
                getParentFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();

            }
        });


              return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargar();
        precio();
    }

    public void cargar() {

        AdapterPedidos listAdapter = new AdapterPedidos(Carritos.pedido, getContext(), new AdapterPedidos.OnItemClickListener() {
            @Override
            public void onItemClick(Pedidos item) {
               borrar(item);
            }
        }, new AdapterPedidos.OnItemClickListener() {
            @Override
            public void onItemClick(Pedidos item) {
                aumentar(item);
            }
        }, new AdapterPedidos.OnItemClickListener() {
            @Override
            public void onItemClick(Pedidos item) {
                disminuir(item);
            }
        });
        contenedorPedidos.setHasFixedSize(true);
        contenedorPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorPedidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorPedidos.setAdapter(listAdapter);
    }
    public void precio() {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        precio_final=formatea.format(Carritos.getPrecioDefinitivo());
        txt_sub_total_carrito.setText(formatea.format(Carritos.getPrecioDefinitivo()) + " ₲");
       txt_descuento_carrito.setText(formatea.format(Carritos.getDescuentoDefinitivo()) + " ₲");
        txt_total_carrito.setText(formatea.format(Carritos.getSubTotalDefinitivo()) + " ₲");
    }
    public void disminuir(Pedidos item) {
        if (item.getCantidad() > 1) {
            Pedidos pedidos = new Pedidos();
            pedidos.setNombre(item.getNombre());
            pedidos.setPrecio_inicial(item.getPrecio_inicial());
            pedidos.setPrecio_real((item.getPrecio_real()/item.getCantidad())*(item.getCantidad() - 1));
            pedidos.setImagen(item.getImagen());
            pedidos.setPrecio_descuento(item.getPrecio_descuento());

            pedidos.setPrecio_descuento_fijo(item.getPrecio_descuento_fijo());
            pedidos.setCantidad(item.getCantidad() - 1);
            int cantidad_actual = item.getCantidad() - 1;
            int precio_unitario = item.getPrecio_total() / item.getCantidad();
            pedidos.setPrecio_total(precio_unitario * cantidad_actual);
            pedidos.setCodigo(item.getCodigo());
            Carritos.agregarPedidos(pedidos);
            cargar();
            precio();
        }
    }
    public void aumentar(Pedidos item) {
        Pedidos pedidos = new Pedidos();
        pedidos.setNombre(item.getNombre());
        pedidos.setImagen(item.getImagen());
        pedidos.setPrecio_descuento_fijo(item.getPrecio_descuento_fijo());
        pedidos.setPrecio_real((item.getPrecio_real()/item.getCantidad())*(item.getCantidad() + 1));
        pedidos.setPrecio_inicial(item.getPrecio_inicial());
        pedidos.setPrecio_descuento(item.getPrecio_descuento());
        pedidos.setCantidad(item.getCantidad() + 1);
        int cantidad_actual = item.getCantidad() + 1;
        int precio_unitario = item.getPrecio_total() / item.getCantidad();
        pedidos.setPrecio_total(precio_unitario * cantidad_actual);
        pedidos.setCodigo(item.getCodigo());
        Carritos.agregarPedidos(pedidos);
        cargar();
        precio();
    }



    public void borrar(Pedidos item) {
        Carritos.pedido.remove(item);
        cargar();
        precio();
    }


    public void borrar_carrito(){
        Carritos.pedido.clear();
        cargar();
        precio();
    }
}