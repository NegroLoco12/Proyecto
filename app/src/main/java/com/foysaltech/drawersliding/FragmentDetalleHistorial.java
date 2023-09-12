package com.foysaltech.drawersliding;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.checkout.paymentbutton.PayPalButton;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class FragmentDetalleHistorial extends Fragment {
    private TextView txt_total_cabecera,txt_fecha_cabecera,txt_estado_cabecera,txt_pedido;
    private List<productos_historial> elements;
    private List<Productos> elements2;
    AdapterDetallesHistorial listAdapterDetallesHistorial;
    private RecyclerView contenedorDetalleHistorial;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Productos productos=new Productos();
    productos_historial productos_historial=new productos_historial();

    public FragmentDetalleHistorial() {

    }

    public static FragmentDetalleHistorial newInstance(String param1, String param2) {
        FragmentDetalleHistorial fragment = new FragmentDetalleHistorial();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_detalle_historial, container, false);
      txt_total_cabecera= view.findViewById(R.id.txt_total_cabecera);
        txt_fecha_cabecera= view.findViewById(R.id.txt_fecha_cabecera);
        txt_estado_cabecera= view.findViewById(R.id.txt_estado_cabecera);
        txt_pedido= view.findViewById(R.id.txt_pedido);
        contenedorDetalleHistorial= view.findViewById(R.id.contenedorDetalleHistorial);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth.setLanguageCode("es");

        getParentFragmentManager().setFragmentResultListener("keyhistorial", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                txt_pedido.setText("Nro de Pedido: "+(result.getInt("numero")+1));
                txt_fecha_cabecera.setText("Fecha del Pedido: "+result.getString("fecha"));
                txt_total_cabecera.setText("Total del Pedido: "+result.getString("total"));
                txt_estado_cabecera.setText("Estado del Pedido: ");
                cargar(result.getString("clave_fk"));

            }
        });
        return view;
    }
    public void cargar( String codigo){

        elements = new ArrayList<>();
        elements2= new ArrayList<>();
        listAdapterDetallesHistorial = new AdapterDetallesHistorial( elements,elements2, getContext(),new AdapterDetallesHistorial.OnItemClickListener() {
            @Override
            public void onItemClick(productos_historial item) {

                //pasar(item);
            }
        });
        contenedorDetalleHistorial.setHasFixedSize(true);
        contenedorDetalleHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorDetalleHistorial.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorDetalleHistorial.setAdapter(listAdapterDetallesHistorial);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        DatabaseReference correo = mDatabase.child("Pedidos_detalles");
        Query nombre = correo.orderByChild("clave_fk").equalTo(codigo);

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    productos_historial=dataSnapshot.getValue(productos_historial.class);
                  //  productos.setKey(dataSnapshot.getKey());
                    elements.add(productos_historial);

                    //   Toast.makeText(getContext(),elements+"",Toast.LENGTH_LONG).show();


                }
                listAdapterDetallesHistorial.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    productos=dataSnapshot.getValue(Productos.class);
                   productos.setKey(dataSnapshot.getKey());
                    elements2.add(productos);

                    //   Toast.makeText(getContext(),elements+"",Toast.LENGTH_LONG).show();


                }
             //   listAdapterDetallesHistorial.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }




}