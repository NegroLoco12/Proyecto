
package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;


public class FragmentDetallePedido extends Fragment {
    Button btn_enviarPedido,btn_agg_ubi;
    private String Instrucciones;
    private List<MetodoEntrega> elements_metodo;
    private TextView txt_sub_total_compra,txt_descuento_compra,txt_delivery_compra,txt_total_compra;
    private List<Ubicaciones> elements_ubicacion;
    private List<Contribuyentes> elements_datosFac;
   private RecyclerView contenedorMetodoEntrega;
  private ConstraintLayout contenedorInstrucciones;
  private CheckBox check_timbre,check_llamar;
    private RecyclerView contenedorUbicacionEntrega,contenedorDatosFacturacion;
   private AdapterMetodoEntrega listAdapterMedodo;
    private AdapterDatosFacturacion listAdapterDatosFacturacion;
    private AdapterUbicacionEntrega listAdapterUbiEntrega;
   private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,chec3_bien,check3,check1,check2,chec2_bien,chec4_bien;
   private CardView cardView1,cardView22,cardView3,cardView4;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Ubicaciones ubicaciones=new Ubicaciones();
    Contribuyentes contribuyentes=new Contribuyentes();
   int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;
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
        imageView1=view.findViewById(R.id.imageView1);
        imageView2=view.findViewById(R.id.imageView2);
        imageView3=view.findViewById(R.id.imageView3);
        imageView4=view.findViewById(R.id.imageView4);
        imageView5=view.findViewById(R.id.imageView5);
        cardView1=view.findViewById(R.id.cardView1);
        cardView22=view.findViewById(R.id.cardView22);
        cardView3=view.findViewById(R.id.cardView3);
        cardView4=view.findViewById(R.id.cardView4);
        txt_total_compra=view.findViewById(R.id.txt_total_compra);
        txt_delivery_compra=view.findViewById(R.id.txt_delivery_compra);
        txt_descuento_compra=view.findViewById(R.id.txt_descuento_compra);
        txt_sub_total_compra=view.findViewById(R.id.txt_sub_total_compra);
        btn_enviarPedido=view.findViewById(R.id.btn_enviarPedido);
        btn_agg_ubi=view.findViewById(R.id.btn_agg_ubi);
        check_llamar=view.findViewById(R.id.check_llamar);
        check_timbre=view.findViewById(R.id.check_timbre);
        chec3_bien=view.findViewById(R.id.chec3_bien);
        check3=view.findViewById(R.id.check3);
        check2=view.findViewById(R.id.check2);
        check1=view.findViewById(R.id.check1);
        chec2_bien=view.findViewById(R.id.chec2_bien);
        chec4_bien=view.findViewById(R.id.chec4_bien);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth.setLanguageCode("es");
        mDatabase= FirebaseDatabase.getInstance().getReference();

        cargarUbi();
        cargar_metodo();
        precio();
        cargarDatosFacturacion();
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
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700);
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
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600);
                    cardView4.setLayoutParams(lparams);
                    d = 1;
                    imageView4.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                }else{
                    contenedorDatosFacturacion.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 250);
                    cardView4.setLayoutParams(lparams);
                    d = 0;
                    imageView4.setImageResource(R.drawable.flecha_hacia_abajo);
                }
            }
        });

        btn_enviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion();
                //cargar_pedido_cabecera();
                //cargar_pedido_detalle();
            }
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
            public void onItemClick() {
                contenedorMetodoEntrega.setVisibility(View.GONE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                cardView1.setLayoutParams(lparams);
                a = 0;
                imageView1.setImageResource(R.drawable.flecha_hacia_abajo);

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
    public void precio() {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        txt_total_compra.setText(formatea.format(Carritos.getSubTotalDefinitivo()) + " ₲");
    }

    /////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public void cargar_pedido_cabecera(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = simpleDateFormat.format(new Date());
        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String fecha = df.format(date);
        String cod_usuario,nombre,total, descuento,delivery;
        nombre="Oscar";
        total=txt_total_compra.getText().toString();
        descuento=txt_descuento_compra.getText().toString();
        delivery=txt_delivery_compra.getText().toString();

        cod_usuario=mAuth.getCurrentUser().getUid();

        Map<String, Object> map = new HashMap<>();

        map.put("cod_usuario", cod_usuario);
        map.put("nombre", nombre);
        map.put("hora", hora);
        map.put("fecha", fecha);
        map.put("total", total);
        map.put("descuento", descuento);
        map.put("delivery", delivery);

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


            map.put("cod_producto", Carritos.pedido.get(i).getCodigo());
            map.put("nombre_producto", nombre);
            map.put("cantidad_producto", Carritos.pedido.get(i).getCantidad());
            map.put("subTotal_producto", nombre);
            mDatabase.child("Pedidos_detalles").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                   //     MotionToast.Companion.createColorToast(getActivity(),
                     //           "Registrado",
                       //         "Registrado sin problemas!",
                         //       MotionToastStyle.SUCCESS,
                           //     MotionToast.GRAVITY_BOTTOM,
                             //   MotionToast.LONG_DURATION,
                               // ResourcesCompat.getFont(getContext(), www.sanju.motiontoast.R.font.helvetica_regular));

                    }
                }
            });
        }

    }

    /////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public boolean validacion(){
        boolean retorno = true;
    if (check_timbre.isChecked()){
        check3.setVisibility(View.VISIBLE);
        chec3_bien.setVisibility(View.GONE);
    }else{
        chec3_bien.setVisibility(View.VISIBLE);
        check3.setVisibility(View.GONE);
         retorno = false;
    }
        if (check_llamar.isChecked()){

            check3.setVisibility(View.VISIBLE);
            chec3_bien.setVisibility(View.GONE);
        }else{

            chec3_bien.setVisibility(View.VISIBLE);
            check3.setVisibility(View.GONE);
            retorno = false;
        }
        return retorno;
    }
/////////////////////////////7//////////////////////////////////////////////////////////////////////////////////////
    public void cargarDatosFacturacion( ){

        elements_datosFac = new ArrayList<>();
        listAdapterDatosFacturacion = new AdapterDatosFacturacion(getContext(), elements_datosFac, new AdapterDatosFacturacion.OnItemClickListener() {
            @Override
            public void onItemClick() {

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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ubicaciones=dataSnapshot.getValue(Ubicaciones.class);
                    ubicaciones.setKey(dataSnapshot.getKey());
                    elements_datosFac.add(contribuyentes);

                    //   Toast.makeText(getApplicationContext(),elementsUbi+"",Toast.LENGTH_LONG).show();


                }
                listAdapterDatosFacturacion.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

}