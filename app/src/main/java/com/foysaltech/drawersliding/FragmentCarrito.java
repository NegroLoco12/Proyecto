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

public class FragmentCarrito extends Fragment {
RecyclerView contenedorPedidos;
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
              return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargar();
    }

    public void cargar() {

        AdapterPedidos listAdapter = new AdapterPedidos(Carritos.pedido, getContext(), new AdapterPedidos.OnItemClickListener()  {
            @Override
        public void onItemClick(Pedidos item) {
                //   borrar(item);

            }
    });

        contenedorPedidos.setHasFixedSize(true);
        contenedorPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorPedidos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorPedidos.setAdapter(listAdapter);
    }
}