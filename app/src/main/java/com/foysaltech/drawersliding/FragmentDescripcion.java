package com.foysaltech.drawersliding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Base64;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class FragmentDescripcion extends Fragment {
    TextView txt_nombre_producto,txt_descripcion_producto,txt_precio_productos,txt_cantidad,txt_descuento_descripcion;
     ImageView imagen;
    int precio_inicial,precio_real;
    Bitmap bitmap;
    int descuentito;
     String codigo_articulo;
    ImageView  button_aumentar, button_disminuir;
    Button btDetailAddToCart;
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
        btDetailAddToCart=view.findViewById(R.id.btDetailAddToCart);
        button_aumentar = view.findViewById(R.id.botton_aumentar);
        button_disminuir = view.findViewById(R.id.botton_disminuir);
        txt_descuento_descripcion= view.findViewById(R.id.txt_descuento_descripcion);
        getParentFragmentManager().setFragmentResultListener("keypro", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                txt_descuento_descripcion.setVisibility(View.GONE);
                txt_descripcion_producto.setText(result.getString("descripcion_producto"));
                txt_nombre_producto.setText(result.getString("nombre_producto"));
                txt_precio_productos.setText(formatea.format(Integer.parseInt(result.getString("precio_producto")))+" Gs");
                precio_inicial=Integer.parseInt(result.getString("precio_producto")) ;
                codigo_articulo=result.getString("cod_producto");
                byte[] byteCode=   Base64.getDecoder().decode(result.getString("imagen_producto"));
                bitmap= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                imagen.setImageBitmap(bitmap);
                precio_real=Integer.parseInt(result.getString("precio_producto"));
                descuentito=0;
              }
        });
        getParentFragmentManager().setFragmentResultListener("keypromo", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                txt_descuento_descripcion.setVisibility(View.VISIBLE);
                txt_descripcion_producto.setText(result.getString("descripcion_producto"));
                txt_nombre_producto.setText(result.getString("nombre_producto"));
                txt_precio_productos.setText(formatea.format(Integer.parseInt(result.getString("descuento_producto")))+" Gs");
                precio_inicial=Integer.parseInt(result.getString("descuento_producto"));
                txt_descuento_descripcion.setText("Antes "+formatea.format(Integer.parseInt(result.getString("precio_producto")))+" Gs");
                codigo_articulo=result.getString("cod_producto");
                byte[] byteCode=   Base64.getDecoder().decode(result.getString("imagen_producto"));
                bitmap= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                imagen.setImageBitmap(bitmap);
                descuentito=Integer.parseInt(result.getString("descuento_producto"));
                precio_real=Integer.parseInt(result.getString("precio_producto"));
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
        btDetailAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });


        return view;
    }
    public void agregar(){
        Pedidos pedidos=new Pedidos();
        int precio_total=precio_inicial*Integer.parseInt(txt_cantidad.getText().toString());
        String nombre=txt_nombre_producto.getText().toString();
        String descripcion=txt_descripcion_producto.getText().toString();
        int cantidad=Integer.parseInt(txt_cantidad.getText().toString());

        pedidos.setCantidad(cantidad);
        pedidos.setPrecio_total(precio_total);
        pedidos.setImagen(bitmap);
        pedidos.setPrecio_inicial(precio_inicial);
        pedidos.setCodigo(codigo_articulo);
        pedidos.setNombre(nombre);
        pedidos.setPrecio_real(precio_real);
        pedidos.setPrecio_descuento(descuentito);
        Carritos.agregarPedidos(pedidos);
        MotionToast.Companion.createColorToast(getActivity(),//Toast Personalizado
                "Exito!",
                "Se añadió a la lista",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getActivity(), www.sanju.motiontoast.R.font.helvetica_regular));

        //   Toast.makeText(activity_descripcion.this,Carritos.pedido.size()+" ", Toast.LENGTH_SHORT).show();
    }
}