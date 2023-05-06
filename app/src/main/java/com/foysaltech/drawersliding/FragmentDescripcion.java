package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentDescripcion extends Fragment {
 TextView txt_nombre_producto,txt_descripcion_producto,txt_precio_productos;
 ImageView imagen;

    public FragmentDescripcion() {

    }

    public static FragmentDescripcion newInstance(String param1, String param2) {
        FragmentDescripcion fragment = new FragmentDescripcion();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_descripcion, container, false);
        txt_descripcion_producto=view.findViewById(R.id.txt_descripcion_producto);

        txt_nombre_producto=view.findViewById(R.id.txt_nombre_producto);

        txt_precio_productos=view.findViewById(R.id.txt_precio_productos);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                txt_descripcion_producto.setText(result.getString("descripcion_producto"));
                txt_nombre_producto.setText(result.getString("nombre_producto"));
                txt_precio_productos.setText(result.getString("precio_producto"));
              }
        });
        return view;
    }
}