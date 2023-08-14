package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentHistorial extends Fragment {
    AdapterHistorial listAdapterHistrial;
    RecyclerView contenedorHistorial;
    private DatabaseReference mDatabase;
    private List<Historial> elementsHistorial;
    Historial historial=new Historial();

    private FirebaseAuth mAuth;

    public FragmentHistorial() {
        // Required empty public constructor
    }

    public static FragmentHistorial newInstance(String param1, String param2) {
        FragmentHistorial fragment = new FragmentHistorial();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_historial, container, false);
        contenedorHistorial=view.findViewById(R.id.contenedorHistorial);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth.setLanguageCode("es");
        cargar();
        return view;
    }
    public void cargar( ){

        elementsHistorial = new ArrayList<>();
        listAdapterHistrial = new AdapterHistorial( elementsHistorial,getContext(), new AdapterHistorial.OnItemClickListener() {
            @Override
            public void onItemClick(Historial item) {

              Bundle bundle=new Bundle();
                bundle.putString("clave_fk",item.getClave_pk());
                bundle.putString("total",item.getTotal());
                bundle.putString("fecha",item.getFecha());
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, new FragmentDetalleHistorial()).addToBackStack(null).commit();
                getParentFragmentManager().setFragmentResult("keyhistorial",bundle);

            }
        });
        contenedorHistorial.setHasFixedSize(true);
        contenedorHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorHistorial.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorHistorial.setAdapter(listAdapterHistrial);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        DatabaseReference correo = mDatabase.child("Pedidos");
        Query nombre = correo.orderByChild("cod_usuario").equalTo(mAuth.getCurrentUser().getUid());

        nombre.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    historial=dataSnapshot.getValue(Historial.class);
                    historial.setKey(dataSnapshot.getKey());
                    elementsHistorial.add(historial);

                    //   Toast.makeText(getContext(),elements+"",Toast.LENGTH_LONG).show();


                }
                listAdapterHistrial.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}