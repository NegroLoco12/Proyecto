package com.foysaltech.drawersliding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentProducto extends Fragment {
TextView txt_cabecera;
    RecyclerView contenedorProducto;
    private DatabaseReference mDatabase;
    RecyclerView.Adapter listAdapter;
    private List<Productos> elements;
Productos productos=new Productos();


    public FragmentProducto() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String codigo= result.getString("cod_categoria");
                String nombre= result.getString("nombre_categoria");
txt_cabecera.setText(nombre);
                //   Toast.makeText(getContext(), codigo+ " ", Toast.LENGTH_LONG).show();
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cargar();

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_productos, container, false);
        contenedorProducto=view.findViewById(R.id.contenedorProducto);
txt_cabecera=view.findViewById(R.id.txt_cabecera);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return view;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void cargar(){

        elements = new ArrayList<>();
        listAdapter = new AdapterProductos(getContext(), elements, new AdapterProductos.OnItemClickListener() {
            @Override
            public void onItemClick(Productos item) {

                //pasar(item);
            }
        });
        contenedorProducto.setHasFixedSize(true);
        contenedorProducto.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorProducto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorProducto.setAdapter(listAdapter);
        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                   productos=dataSnapshot.getValue(Productos.class);
                   // productos.setKey(dataSnapshot.getKey());
                  elements.add(productos);

                    //   Toast.makeText(getContext(),elements+"",Toast.LENGTH_LONG).show();


                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}