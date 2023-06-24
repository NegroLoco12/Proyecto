
package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentDetallePedido extends Fragment {


    private List<MetodoEntrega> elements_metodo;
    private List<Ubicaciones> elements_ubicacion;
   private RecyclerView contenedorMetodoEntrega;
    private RecyclerView contenedorUbicacionEntrega;
   private AdapterMetodoEntrega listAdapterMedodo;
    private AdapterUbicacionEntrega listAdapterUbiEntrega;
   private ImageView imageView1,imageView2;
   private CardView cardView1,cardView22;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Ubicaciones ubicaciones=new Ubicaciones();
   int a=0;
    int b=0;
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
        imageView1=view.findViewById(R.id.imageView1);
        imageView2=view.findViewById(R.id.imageView2);
        cardView1=view.findViewById(R.id.cardView1);
        cardView22=view.findViewById(R.id.cardView22);
        mDatabase= FirebaseDatabase.getInstance().getReference();
////        mAuth.setLanguageCode("es");

        cargarUbi();
        cargar_metodo();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 0) {
                    contenedorMetodoEntrega.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700);
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
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 700);
                    cardView22.setLayoutParams(lparams);
                    b = 1;
                    imageView2.setImageResource(R.drawable.punta_de_flecha_hacia_arriba);
                }else{
                    contenedorUbicacionEntrega.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                    cardView22.setLayoutParams(lparams);
                    b = 0;
                    imageView2.setImageResource(R.drawable.flecha_hacia_abajo);
                }
            }
        });


        return view;
    }

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
            public void onItemClick(Categorias item) {

            }
        });
        contenedorMetodoEntrega.setHasFixedSize(true);
        contenedorMetodoEntrega.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorMetodoEntrega.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorMetodoEntrega.setAdapter(listAdapterMedodo);
    }


    public void cargarUbi( ){

        elements_ubicacion = new ArrayList<>();
        listAdapterUbiEntrega = new AdapterUbicacionEntrega(getContext(), elements_ubicacion, new AdapterUbicacionEntrega.OnItemClickListener() {
            @Override
            public void onItemClick(Ubicaciones item) {
                //borrarUbi(item);
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

}