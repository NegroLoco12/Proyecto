package com.foysaltech.drawersliding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;


public class CenteredTextFragment extends Fragment {
    RecyclerView contenedorMenu;
    private DatabaseReference mDatabase;
    RecyclerView.Adapter listAdapter;
    private List<Categorias> elements;
    private static final String EXTRA_TEXT = "text";
    Categorias categorias=new Categorias();

    public static CenteredTextFragment createFor(String text) {

        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                View view=inflater.inflate(R.layout.fragment_text, container, false);
                contenedorMenu=view.findViewById(R.id.contenedorMenu);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cargar();
        Bundle args = getArguments();
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
        TextView textView = view.findViewById(R.id.cabecera);
        if (text.equals("Usuario")){
                  FirebaseAuth.getInstance().signOut();
                 Intent intent=new Intent(getContext(),LoginActivity.class);
                 startActivity(intent);

}
        textView.setText(text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


            }
        });
    }
    public void cargar(){

        elements = new ArrayList<>();
        listAdapter = new AdapterCategoria(getContext(), elements, new AdapterCategoria.OnItemClickListener() {
            @Override
            public void onItemClick(Categorias item) {

                pasar(item);
            }
        });
        contenedorMenu.setHasFixedSize(true);
        contenedorMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorMenu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        contenedorMenu.setAdapter(listAdapter);
        mDatabase.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                     categorias=dataSnapshot.getValue(Categorias.class);
                    categorias.setKey(dataSnapshot.getKey());
                    elements.add(categorias);

                    //   Toast.makeText(getContext(),elements+"",Toast.LENGTH_LONG).show();


                }
                 listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
    public void pasar(Categorias item){

   // Toast.makeText(getContext(), categorias.getKey(0)+ " ", Toast.LENGTH_LONG).show();

        //FragmentManager manager = getActivity().getSupportFragmentManager();
       // manager.beginTransaction().replace(R.id.container, new FragmentProducto()).commit();
    }

}
