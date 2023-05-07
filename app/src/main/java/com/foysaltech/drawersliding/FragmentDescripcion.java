package com.foysaltech.drawersliding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Base64;


public class FragmentDescripcion extends Fragment {
 TextView txt_nombre_producto,txt_descripcion_producto,txt_precio_productos,txt_cantidad;
 ImageView imagen;
 int precio_inicial;
    ImageView  button_aumentar, button_disminuir;

    DecimalFormat formatea = new DecimalFormat("###,###.##");

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
        txt_cantidad = view.findViewById(R.id.txt_cantidad);
        txt_nombre_producto=view.findViewById(R.id.txt_nombre_producto);
        imagen=view.findViewById(R.id.imagen);
        txt_precio_productos=view.findViewById(R.id.txt_precio_productos);

        button_aumentar = view.findViewById(R.id.botton_aumentar);
        button_disminuir = view.findViewById(R.id.botton_disminuir);
        getParentFragmentManager().setFragmentResultListener("keypro", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                txt_descripcion_producto.setText(result.getString("descripcion_producto"));
                txt_nombre_producto.setText(result.getString("nombre_producto"));
                txt_precio_productos.setText(result.getString("precio_producto")+" Gs");
                precio_inicial=Integer.parseInt(result.getString("precio_producto")) ;
                Bitmap bitmap;
                byte[] byteCode=   Base64.getDecoder().decode(result.getString("imagen_producto"));
                bitmap= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                imagen.setImageBitmap(bitmap);
              }
        });
        button_aumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int b = Integer.parseInt(txt_cantidad.getText().toString());
                int c = b + 1;
                txt_cantidad.setText(c + "");
                int suma = (b + 1) * precio_inicial;
                txt_precio_productos.setText(formatea.format(suma )+ " ₲");
            }
        });
        button_disminuir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int b = Integer.parseInt(txt_cantidad.getText().toString());
                if (b > 1) {

                    int c = b - 1;
                    txt_cantidad.setText(c + "");
                    int suma = (b - 1) * precio_inicial;
                    txt_precio_productos.setText(formatea.format(suma )+ " ₲");
                }
            }
        });



        return view;
    }
}