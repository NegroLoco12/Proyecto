
package com.foysaltech.drawersliding;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;


import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class FragmentDetallePedido extends Fragment {
    private RequestQueue queue;
    private static String accessToken;

    Object moneda_america,moneda_paragua;

    int validacion1,validacion2,validacion3,validacion4=0;
    private Button btn_enviarPedido,btn_agg_ubi,btn_add_datos,btnPaypal;
    private String metodo_entrega, direccion_entrega,instrucciones_entrega,datos_facturacion,metodo_pago,metodo_pago_online;
    private List<MetodoEntrega> elements_metodo;
    private TextView txt_sub_total_compra,txt_descuento_compra,txt_delivery_compra,txt_total_compra;
    private List<Ubicaciones> elements_ubicacion;
    private List<Contribuyentes> elements_datosFac;
   private RecyclerView contenedorMetodoEntrega,contenedorMetodoPago;
    private String clave;

  private ConstraintLayout contenedorInstrucciones;
  private CheckBox check_timbre,check_llamar;
    private RecyclerView contenedorUbicacionEntrega,contenedorDatosFacturacion,contenedorMetodoPagoOnline;
   private AdapterMetodoEntrega listAdapterMedodo;
    private AdapterMetodoPago listAdapterMedodoPago;
    private AdapterDatosFacturacion listAdapterDatosFacturacion;
    private AdapterUbicacionEntrega listAdapterUbiEntrega;
   private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,chec3_bien,check3,check1,check4,check2,chec2_bien,chec4_bien,chec1_bien,chec5_bien,check5,chec6_bien,check6;
   private CardView cardView1,cardView22,cardView3,cardView4,cardView5,cardView6;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Ubicaciones ubicaciones=new Ubicaciones();
    Contribuyentes contribuyentes=new Contribuyentes();


    int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;
    int f=0;
    public FragmentDetallePedido() {

    }

    public static FragmentDetallePedido newInstance(String param1, String param2) {
        FragmentDetallePedido fragment = new FragmentDetallePedido();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_detalle_pedido, container, false);
        contenedorMetodoEntrega=view.findViewById(R.id.contenedorMetodoEntrega);
        contenedorUbicacionEntrega=view.findViewById(R.id.contenedorUbicacionEntrega);
        contenedorDatosFacturacion=view.findViewById(R.id.contenedorDatosFacturacion);
        contenedorInstrucciones=view.findViewById(R.id.contenedorInstrucciones);
        contenedorMetodoPagoOnline=view.findViewById(R.id.contenedorMetodoPagoOnline);
        contenedorMetodoPago=view.findViewById(R.id.contenedorMetodoPago);
        imageView1=view.findViewById(R.id.imageView1);
        imageView2=view.findViewById(R.id.imageView2);
        imageView3=view.findViewById(R.id.imageView3);
        imageView4=view.findViewById(R.id.imageView4);
        imageView5=view.findViewById(R.id.imageView5);
        imageView6=view.findViewById(R.id.imageView6);
        cardView1=view.findViewById(R.id.cardView1);
        cardView22=view.findViewById(R.id.cardView22);
        cardView3=view.findViewById(R.id.cardView3);
        cardView4=view.findViewById(R.id.cardView4);
        cardView5=view.findViewById(R.id.cardView5);
        cardView6=view.findViewById(R.id.cardView6);
        txt_total_compra=view.findViewById(R.id.txt_total_compra);
        txt_delivery_compra=view.findViewById(R.id.txt_delivery_compra);
        txt_descuento_compra=view.findViewById(R.id.txt_descuento_compra);
        txt_sub_total_compra=view.findViewById(R.id.txt_sub_total_compra);
        btn_enviarPedido=view.findViewById(R.id.btn_enviarPedido);
        btn_agg_ubi=view.findViewById(R.id.btn_agg_ubi);
        btn_add_datos=view.findViewById(R.id.btn_add_datos);
        btnPaypal=view.findViewById(R.id.btnPaypal);
        check_llamar=view.findViewById(R.id.check_llamar);
        check_timbre=view.findViewById(R.id.check_timbre);
        chec3_bien=view.findViewById(R.id.chec3_bien);
        check3=view.findViewById(R.id.check3);
        check5=view.findViewById(R.id.check5);
        check2=view.findViewById(R.id.check2);
        check4=view.findViewById(R.id.check4);
        check1=view.findViewById(R.id.check1);
        chec2_bien=view.findViewById(R.id.chec2_bien);
        chec4_bien=view.findViewById(R.id.chec4_bien);
        chec6_bien=view.findViewById(R.id.chec6_bien);
        check6=view.findViewById(R.id.check6);
        chec5_bien=view.findViewById(R.id.chec5_bien);
        chec1_bien=view.findViewById(R.id.chec1_bien);
        queue= Volley.newRequestQueue(getActivity());
        calcularCambio();
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth.setLanguageCode("es");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        getAccessToken();

        btnPaypal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                createOrder(); //this will trigger the checkout flow
            }
        });





        cargarUbi();
        cargar_metodo();
        precio();
        cargarDatosFacturacion();
        cargar_metodo_pago();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 0) {
                    contenedorMetodoEntrega.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 550);
                    cardView1.setLayoutParams(lparams);
                    a = 1;
                    imageView1.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                }else{
                    contenedorMetodoEntrega.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                    cardView1.setLayoutParams(lparams);
                    a = 0;
                    imageView1.setImageResource(R.drawable.flecha_hacia_abajo);
                }
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b == 0) {
                    contenedorUbicacionEntrega.setVisibility(View.VISIBLE);
                    btn_agg_ubi.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 670);
                    cardView22.setLayoutParams(lparams);
                    b = 1;
                    imageView2.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                }else{
                    contenedorUbicacionEntrega.setVisibility(View.GONE);
                    btn_agg_ubi.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                    cardView22.setLayoutParams(lparams);
                    b = 0;
                    imageView2.setImageResource(R.drawable.flecha_hacia_abajo);

                }
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c == 0) {
                    contenedorInstrucciones.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600);
                    cardView3.setLayoutParams(lparams);
                    c= 1;
                    imageView3.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                }else{
                    contenedorInstrucciones.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                    cardView3.setLayoutParams(lparams);
                    c = 0;
                    imageView3.setImageResource(R.drawable.flecha_hacia_abajo);
                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 0) {
                    contenedorDatosFacturacion.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 670);
                    cardView4.setLayoutParams(lparams);
                    d = 1;
                    imageView4.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                    btn_add_datos.setVisibility(View.VISIBLE);
                }else{
                    contenedorDatosFacturacion.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                    cardView4.setLayoutParams(lparams);
                    d = 0;
                    imageView4.setImageResource(R.drawable.flecha_hacia_abajo);
                    btn_add_datos.setVisibility(View.GONE);
                }
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e == 0) {
                    contenedorMetodoPago.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 610);
                    cardView5.setLayoutParams(lparams);
                    e = 1;
                    imageView5.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);

                }else{
                    contenedorMetodoPago.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                    cardView5.setLayoutParams(lparams);
                    e = 0;
                    imageView5.setImageResource(R.drawable.flecha_hacia_abajo);

                }
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (f == 0) {
                    contenedorMetodoPagoOnline.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 610);
                    cardView6.setLayoutParams(lparams);
                    f = 1;
                    imageView6.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);

                }else{
                    contenedorMetodoPagoOnline.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                    cardView6.setLayoutParams(lparams);
                    f = 0;
                    imageView6.setImageResource(R.drawable.flecha_hacia_abajo);

                }
            }
        });
        btn_enviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(validacion()){

                cargar_pedido_cabecera();
                cargar_pedido_detalle();
         }}
        });
        check_timbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_timbre.isChecked()){
                    chec3_bien.setVisibility(View.GONE);
                    check3.setVisibility(View.VISIBLE);


                }else{
                    chec3_bien.setVisibility(View.VISIBLE);
                    check3.setVisibility(View.GONE);

                }
            }
        });
        check_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_llamar.isChecked()){
                    chec3_bien.setVisibility(View.GONE);
                    check3.setVisibility(View.VISIBLE);

                }else{
                    chec3_bien.setVisibility(View.VISIBLE);
                    check3.setVisibility(View.GONE);

                }

            }
        });
        btn_agg_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapaFragment fragment = new MapaFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment, "fragment_meters");
                ft.addToBackStack(null);  //opcional, si quieres agregarlo a la pila
                ft.commit();
                Bundle data = new Bundle();
                data.putString("dato", "Nuevo");

                fragment.setArguments(data);;

            }
        });
        btn_add_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());

                View view1=getLayoutInflater().inflate(R.layout.custom_dialog_contribuyente,null);
                builder.setView(view1);
                builder.setCancelable(true);
                AlertDialog  alertDialog= builder.create();
                alertDialog.show();
                view1.findViewById(R.id.btn_guardar_contri).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cod_usuario=mAuth.getCurrentUser().getUid();
                        EditText txt_ruc=view1.findViewById(R.id.txt_ruc);
                        EditText txt_razonsocial=view1.findViewById(R.id.txt_razonsocial);
                        String nombre=txt_razonsocial.getText().toString();
                        String ruc=txt_ruc.getText().toString();
                        Map<String, Object> map = new HashMap<>();
                        map.put("razon_social", nombre);
                        map.put("documento", ruc);
                        map.put("cod_usuario", cod_usuario);
                        String id = mAuth.getCurrentUser().getUid();
                        mDatabase.child("Contribuyentes").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()) {
                                    MotionToast.Companion.createColorToast(getActivity(),//Toast Personalizado
                                            "Registrado",
                                            "Registrado sin problemas!",
                                            MotionToastStyle.SUCCESS,
                                            MotionToast.GRAVITY_BOTTOM,
                                            MotionToast.LONG_DURATION,
                                            ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));
                                    alertDialog.dismiss();
                                    //elements_datosFac.clear();
                                    //cargarDatosFacturacion();

                                }
                            }
                        });
                    }
                });

                // alertDialog.setCanceledOnTouchOutside(false);
                // bottomSheetDialog.dismiss();

            }
        });




        return view;
    }

/////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////

    public void cargar_metodo(){
        MetodoEntrega metodoEntrega=new MetodoEntrega();
        MetodoEntrega metodoEntrega1=new MetodoEntrega();
        metodoEntrega.setNombre("Delivery");
        metodoEntrega1.setNombre("Pickup");
        elements_metodo = new ArrayList<>();
        elements_metodo.add(0,metodoEntrega);
        elements_metodo.add(1,metodoEntrega1);
        listAdapterMedodo = new AdapterMetodoEntrega(getContext(), elements_metodo, new AdapterMetodoEntrega.OnItemClickListener()  {
            @Override
            public void onItemClick(MetodoEntrega item) {
                contenedorMetodoEntrega.setVisibility(View.GONE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                cardView1.setLayoutParams(lparams);
                a = 0;
                imageView1.setImageResource(R.drawable.flecha_hacia_abajo);
                validacion1=1;
                check1.setVisibility(View.VISIBLE);
                chec1_bien.setVisibility(View.GONE);
                metodo_entrega=item.getNombre();
            }
        });
        contenedorMetodoEntrega.setHasFixedSize(true);
        contenedorMetodoEntrega.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorMetodoEntrega.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorMetodoEntrega.setAdapter(listAdapterMedodo);
    }

/////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////

    public void cargarUbi( ){

        elements_ubicacion = new ArrayList<>();
        listAdapterUbiEntrega = new AdapterUbicacionEntrega(getContext(), elements_ubicacion, new AdapterUbicacionEntrega.OnItemClickListener() {
            @Override
            public void onItemClick(Ubicaciones item) {
                contenedorUbicacionEntrega.setVisibility(View.GONE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                cardView22.setLayoutParams(lparams);
                b = 0;
                imageView2.setImageResource(R.drawable.flecha_hacia_abajo);
                check2.setVisibility(View.VISIBLE);
                chec2_bien.setVisibility(View.GONE);
                validacion2=1;
                btn_agg_ubi.setVisibility(View.GONE);
                direccion_entrega=item.getKey();
            }


        });
        contenedorUbicacionEntrega.setHasFixedSize(true);
        contenedorUbicacionEntrega.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorUbicacionEntrega.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorUbicacionEntrega.setAdapter(listAdapterUbiEntrega);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        DatabaseReference correo = mDatabase.child("Direcciones");
        Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ubicaciones=dataSnapshot.getValue(Ubicaciones.class);
                    ubicaciones.setKey(dataSnapshot.getKey());
                    elements_ubicacion.add(ubicaciones);

                    //   Toast.makeText(getApplicationContext(),elementsUbi+"",Toast.LENGTH_LONG).show();


                }
                listAdapterUbiEntrega.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void precio() {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        txt_total_compra.setText(formatea.format(Carritos.getSubTotalDefinitivo()) + " ₲");
        txt_descuento_compra.setText(formatea.format(Carritos.getDescuentoDefinitivo()) + " ₲");
        txt_sub_total_compra.setText(formatea.format(Carritos.getPrecioDefinitivo()) + " ₲");
    }

    /////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public void cargar_pedido_cabecera(){
        clave= UUID.randomUUID().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = simpleDateFormat.format(new Date());
        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String fecha = df.format(date);
        String cod_usuario,total, descuento,delivery;

        total=txt_total_compra.getText().toString();
        descuento=txt_descuento_compra.getText().toString();
        delivery=txt_delivery_compra.getText().toString();
        if (check_timbre.isChecked()){
        instrucciones_entrega="Tocar el Timbre";
        }
        if (check_llamar.isChecked()){
            instrucciones_entrega="LLamar al llegar ";
        }
        if (check_llamar.isChecked() && check_timbre.isChecked() ){
            instrucciones_entrega="Tocar el Timbre y LLamar al llegar ";
        }
        cod_usuario=mAuth.getCurrentUser().getUid();

        Map<String, Object> map = new HashMap<>();
        map.put("clave_pk", clave);
        map.put("cod_usuario", cod_usuario);
        map.put("hora", hora);
        map.put("fecha", fecha);
        map.put("total", total);
        map.put("descuento", descuento);
        map.put("delivery", delivery);
        map.put("estado", true);
        map.put("metodo_entrega", metodo_entrega);
        map.put("direccion_entrega", direccion_entrega);
        map.put("instrucciones_entrega", instrucciones_entrega);
        map.put("datos_facturacion", datos_facturacion);
        map.put("metodo_pago", metodo_pago);
       // map.put("metodo_pago_online", metodo_pago_online);
        mDatabase.child("Pedidos").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MotionToast.Companion.createColorToast(getActivity(),
                            "Registrado",
                            "Registrado sin problemas!",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));
                    Fragment selectedScreen = new CenteredTextFragment();
                    getParentFragmentManager().beginTransaction().replace(R.id.container, selectedScreen).commit();
                    Carritos.pedido.clear();

                }
            }
        });
    }

    /////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public void cargar_pedido_detalle() {
        for (int i = 0; i < Carritos.pedido.size(); i++) {
            String cod_usuario, nombre, total, descuento, delivery;
            nombre = Carritos.pedido.get(i).getNombre();

            cod_usuario = mAuth.getCurrentUser().getUid();

            Map<String, Object> map = new HashMap<>();

            map.put("clave_fk", clave);
            map.put("cod_producto", Carritos.pedido.get(i).getCodigo());
            map.put("nombre_producto", nombre);
            map.put("cantidad_producto", Carritos.pedido.get(i).getCantidad());

            map.put("descuento_producto", Carritos.pedido.get(i).getPrecio_descuento());
            map.put("subTotal_producto",  Carritos.pedido.get(i).getPrecio_total());
            mDatabase.child("Pedidos_detalles").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        }

    }

    /////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public boolean validacion(){
        boolean retorno = true;
    if (check_timbre.isChecked() || check_llamar.isChecked()){
        check3.setVisibility(View.VISIBLE);
        chec3_bien.setVisibility(View.GONE);

    }else{
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1500);
        chec3_bien.setVisibility(View.VISIBLE);
        check3.setVisibility(View.GONE);
         retorno = false;
    }



        if (validacion1==1){

            check1.setVisibility(View.VISIBLE);
            chec1_bien.setVisibility(View.GONE);
        }else{
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            chec1_bien.setVisibility(View.VISIBLE);
            check1.setVisibility(View.GONE);
            retorno = false;
        }

        if (validacion2==1){

            check2.setVisibility(View.VISIBLE);
            chec2_bien.setVisibility(View.GONE);
        }else{
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            chec2_bien.setVisibility(View.VISIBLE);
            check2.setVisibility(View.GONE);
            retorno = false;
        }
        if (validacion3==1){

            check4.setVisibility(View.VISIBLE);
            chec4_bien.setVisibility(View.GONE);
        }else{
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            chec4_bien.setVisibility(View.VISIBLE);
            check4.setVisibility(View.GONE);
            retorno = false;
        }
        if (validacion4==1){

            check5.setVisibility(View.VISIBLE);
            chec5_bien.setVisibility(View.GONE);
        }else{
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1500);
            chec5_bien.setVisibility(View.VISIBLE);
            check5.setVisibility(View.GONE);
            retorno = false;
        }
        return retorno;
    }
/////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public void cargarDatosFacturacion( ){

        elements_datosFac = new ArrayList<>();
        listAdapterDatosFacturacion = new AdapterDatosFacturacion(getContext(), elements_datosFac, new AdapterDatosFacturacion.OnItemClickListener() {
            @Override
            public void onItemClick(Contribuyentes item) {
                contenedorDatosFacturacion.setVisibility(View.GONE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 240);
                cardView4.setLayoutParams(lparams);
                d = 0;
                imageView4.setImageResource(R.drawable.flecha_hacia_abajo);
                btn_add_datos.setVisibility(View.GONE);
                check4.setVisibility(View.VISIBLE);
                chec4_bien.setVisibility(View.GONE);
                datos_facturacion=item.getKey();
                validacion3=1;
            }
        });
        contenedorDatosFacturacion.setHasFixedSize(true);
        contenedorDatosFacturacion.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorDatosFacturacion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorDatosFacturacion.setAdapter(listAdapterDatosFacturacion);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        DatabaseReference correo = mDatabase.child("Contribuyentes");
        Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements_datosFac.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
       //             Toast.makeText(getActivity().getApplicationContext(),dataSnapshot.getKey()+ "",Toast.LENGTH_LONG).show();

                    contribuyentes=dataSnapshot.getValue(Contribuyentes.class);
                    contribuyentes.setKey(dataSnapshot.getKey());
                    elements_datosFac.add(contribuyentes);
                //    filter(dataSnapshot.getKey());
               ///

                }
              //  Toast.makeText(getActivity().getApplicationContext(), "ENTRA",Toast.LENGTH_LONG).show();

                   listAdapterDatosFacturacion.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void cargar_metodo_pago(){
    listAdapterMedodoPago = new AdapterMetodoPago(getContext(), new AdapterMetodoPago.OnItemClickListener() {
        @Override
        public void onItemClickPos() {
            contenedorMetodoPago.setVisibility(View.GONE);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            cardView5.setLayoutParams(lparams);
            d = 0;
            imageView5.setImageResource(R.drawable.flecha_hacia_abajo);
            check5.setVisibility(View.VISIBLE);
            chec5_bien.setVisibility(View.GONE);

            cardView6.setVisibility(View.GONE);
            metodo_pago="POS";
            validacion4=1;
        }

        @Override
        public void onItemClickEfectivo() {
            contenedorMetodoPago.setVisibility(View.GONE);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            cardView5.setLayoutParams(lparams);
            d = 0;
            imageView5.setImageResource(R.drawable.flecha_hacia_abajo);
            check5.setVisibility(View.VISIBLE);
            chec5_bien.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            metodo_pago="EFECTIVO";
            validacion4=1;
        }

        @Override
        public void onItemClickOline() {
            contenedorMetodoPago.setVisibility(View.GONE);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            cardView5.setLayoutParams(lparams);
            d = 0;
            imageView5.setImageResource(R.drawable.flecha_hacia_abajo);
            check5.setVisibility(View.VISIBLE);
            chec5_bien.setVisibility(View.GONE);
            cardView6.setVisibility(View.VISIBLE);
            metodo_pago="ONLINE";
            validacion4=1;

        }
    });
    contenedorMetodoPago.setHasFixedSize(true);
    contenedorMetodoPago.setLayoutManager(new LinearLayoutManager(getContext()));
    contenedorMetodoPago.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    contenedorMetodoPago.setAdapter(listAdapterMedodoPago);
}

////////////////////////////////////////////////////////////////////////////////////////////////////


    private void calcularCambio(){
        String apiKey="ff0ceedeeda8e7ef572244613b0dfd00";
        String symbolos="PYG,USD";
        String url="http://data.fixer.io/api/latest?access_key="+apiKey+"&symbols="+symbolos+"&format=1";
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
        try {
           // String base=response.getString("name");
            JSONObject rates= response.getJSONObject("rates");
            Iterator<String> itr=rates.keys();
          //  while(itr.hasNext()){
                String key =itr.next();
                 moneda_paragua =rates.get("PYG");
                 moneda_america =rates.get("USD");
              //  Toast.makeText(getContext(),moneda_america+" "+modeda_paragua,Toast.LENGTH_SHORT).show();
           // }
        }catch (JSONException e){
            Toast.makeText(getContext(),e+"",Toast.LENGTH_SHORT).show();
        }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getContext(),error+"",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////


    String encodeStringToBase64(){
        String input = "AQ-XwepAEfVeRbs57LsQ5hP1TaOkuV3ECVWD7T8cjW8N-rPmlekqffWaISmKMfLyjqT5H3vrEYYIaRqo:EKj5Onvud9DN1-fj6KHMt8WCxCF1gmSIne4HFPS9rH45DGhno_0RzoeiXcITFwMxhLvgUsw-krEWm4u_";
        String encodedString = Base64.getEncoder().encodeToString(input.getBytes());
        return encodedString;
    }

    void getAccessToken(){
        String AUTH = encodeStringToBase64();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/x-www-form-urlencoded");
        client.addHeader("Authorization", "Basic "+ AUTH);
        String jsonString = "grant_type=client_credentials";

        HttpEntity entity = new StringEntity(jsonString, "utf-8");

        client.post(getContext(), "https://api-m.sandbox.paypal.com/v1/oauth2/token", entity, "application/x-www-form-urlencoded",new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                try {
                    JSONObject jobj = new JSONObject(responseString);

                    accessToken = jobj.getString("access_token");
                    Log.e("accessToken", accessToken);
                } catch (JSONException e) {
                    //    e.printStackTrace();
                }
            }

        });
    }



    //////////////////////////////////////////////////////////////////////////////////////////////
public void createOrder(){
    AsyncHttpClient client = new AsyncHttpClient();
    client.addHeader("Accept", "application/json");
    client.addHeader("Content-type", "application/json");
    client.addHeader("Authorization", "Bearer " + accessToken);

    String order = "{"
            + "\"intent\": \"CAPTURE\","
            + "\"purchase_units\": [\n" +
            "      {\n" +
            "        \"amount\": {\n" +
            "          \"currency_code\": \"USD\",\n" +
            "          \"value\": \"500.00\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\"application_context\": {\n" +
            "        \"brand_name\": \"TEST_STORE\",\n" +
            "        \"return_url\": \"https://closed-chaplain.000webhostapp.com/usuario\",\n" +
            "        \"cancel_url\": \"http://example.com\"\n" +
            "    }}";
    HttpEntity entity = new StringEntity(order, "utf-8");

    client.post(getContext(), "https://api-m.sandbox.paypal.com/v2/checkout/orders", entity, "application/json",new TextHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
            Log.e("RESPONSE", responseString);
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
            Log.e("RESPONSE", responseString);
            try {
                JSONArray links = new JSONObject(responseString).getJSONArray("links");

                //iterate the array to get the approval link
                for (int i = 0; i < links.length(); ++i) {
                    String rel = links.getJSONObject(i).getString("rel");
                    if (rel.equals("approve")){
                        JSONArray linkObj=new JSONArray();
                        String link = links.getJSONObject(i).getString("href");
                        //redirect to this link via CCT
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(getContext(), Uri.parse(link));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    });
}
    public static String getMyAccessToken(){
        return accessToken;
    }


}