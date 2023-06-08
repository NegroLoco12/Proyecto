package com.foysaltech.drawersliding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;


public class CenteredTextFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView contenedorMenu;

    RecyclerView contenedorTodo,contenedorPromo;
    private DatabaseReference mDatabase;
   AdapterCategoria listAdapter;
   AdapterProductos listaAdapterProducto;
    private TextView cabecera2;

    AdapterPromo listaAdapterPromo;
    private List<Categorias> elements;
    private List<Productos> elementsProductos;

    private List<Productos> elementsPromo;
    private static final String EXTRA_TEXT = "text";
    Categorias categorias=new Categorias();
    Productos productos=new Productos();
    SearchView txtBuscar;

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
        contenedorTodo=view.findViewById(R.id.contenedorTodos);
        contenedorPromo=view.findViewById(R.id.contenedorPromo);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       cargar();
        cargarTodo();
        cargarPromo();
        Bundle args = getArguments();
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
        TextView textView = view.findViewById(R.id.cabecera);
        cabecera2=view.findViewById(R.id.cabecera2);
        txtBuscar=view.findViewById(R.id.MenuSearch);
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
        txtBuscar.setOnQueryTextListener(this);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void pasar(Categorias item){

   // Toast.makeText(getContext(), categorias.getKey(0)+ " ", Toast.LENGTH_LONG).show();
        Bundle bundle=new Bundle();
        bundle.putString("cod_categoria",item.getKey());
        bundle.putString("nombre_categoria",item.getDescripcion());
        FragmentManager manager = getActivity().getSupportFragmentManager();
       manager.beginTransaction().replace(R.id.container, new FragmentProducto()).addToBackStack(null).commit();
       getParentFragmentManager().setFragmentResult("key",bundle);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        int longitud=newText.length();
        //filter(newText);
         if (longitud==0){

             //Toast.makeText(getContext(),"ss",Toast.LENGTH_LONG).show();
            contenedorTodo.setVisibility(View.GONE);
            contenedorMenu.setVisibility(View.VISIBLE);

            }
        if (longitud>0) {
           if (filter(newText) == false) {
                contenedorTodo.setVisibility(View.GONE);
                contenedorMenu.setVisibility(View.VISIBLE);

            }
            if (filter(newText)== true) {
              //  if (longitud != 0) {
                  contenedorTodo.setVisibility(View.VISIBLE);
                  contenedorMenu.setVisibility(View.GONE);
                    // cabecera2.setVisibility(View.GONE);
               }
            }
       // }
        return true;
    }

    public boolean filter(String Text) {
        boolean a=true;
        List<Productos> filetredList=new ArrayList<>();
        for(Productos productos:elementsProductos){
            if(productos.getDescripcion().toLowerCase().contains(Text.toLowerCase())){
                filetredList.add(productos);
            }
        }
        if(filetredList.isEmpty()){
            a=false;
        }else{
           // contenedorTodo.setVisibility(View.GONE);
           //   contenedorMenu.setVisibility(View.VISIBLE);

            listaAdapterProducto.setFilter(filetredList);
        }
        return a;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void cargarTodo( ){

        elementsProductos = new ArrayList<>();
        listaAdapterProducto = new AdapterProductos(getContext(), elementsProductos,elementsProductos, new AdapterProductos.OnItemClickListener() {
            @Override
            public void onItemClick(Productos item) {

                //pasar(item);
            }
        });
        contenedorTodo.setHasFixedSize(true);
        contenedorTodo.setLayoutManager(new LinearLayoutManager(getContext()));
        contenedorTodo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        contenedorTodo.setAdapter(listaAdapterProducto);
        // Toast.makeText(getContext(),codigo+"",Toast.LENGTH_LONG).show();

        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    productos=dataSnapshot.getValue(Productos.class);
                    // productos.setKey(dataSnapshot.getKey());
                    elementsProductos.add(productos);

             //         Toast.makeText(getContext(),elementsProductos+"",Toast.LENGTH_LONG).show();


                }
                listaAdapterProducto.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void cargarPromo( ){

    elementsPromo= new ArrayList<>();
    listaAdapterPromo = new AdapterPromo(elementsPromo,getContext(),new AdapterPromo.OnItemClickListener() {
        @Override
        public void onItemClick(Productos item) {

            pasar_promo(item);
        }
    });
    LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    contenedorPromo.setHasFixedSize(true);
    contenedorPromo.setLayoutManager(new LinearLayoutManager(getContext()));
    contenedorPromo.setLayoutManager(layoutManager);
    contenedorPromo.setAdapter(listaAdapterPromo);
    LinearSnapHelper linearSnapHelper=new LinearSnapHelper();
    Timer timer= new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
         //   int primer=layoutManager.findLastCompletelyVisibleItemPosition();
            int primer=0;
            int segundo=listaAdapterPromo.getItemCount();
            if(primer<segundo){
                primer++;
               String a=Carritos.pedido.size()+"";
               String b=(listaAdapterPromo.getItemCount()-1)+"";

                layoutManager.smoothScrollToPosition(contenedorPromo,new RecyclerView.State(),layoutManager.findFirstVisibleItemPosition()+1);
                if(primer==segundo)  {
                    layoutManager.smoothScrollToPosition(contenedorPromo,new RecyclerView.State(),0);
                    String ab=layoutManager.findFirstVisibleItemPosition()+"Funcionaaa";

                }
            }

        }
    },0,5000);

    DatabaseReference correo = mDatabase.child("Productos");
    Query nombre = correo.orderByChild("estado_promo").equalTo(true);

    nombre.addValueEventListener(new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                productos=dataSnapshot.getValue(Productos.class);
                // productos.setKey(dataSnapshot.getKey());
                elementsPromo.add(productos);

                //         Toast.makeText(getContext(),elementsProductos+"",Toast.LENGTH_LONG).show();


            }
            listaAdapterPromo.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }


    });
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void pasar_promo(Productos item){

    // Toast.makeText(getContext(), categorias.getKey(0)+ " ", Toast.LENGTH_LONG).show();
    Bundle bundle=new Bundle();
    bundle.putString("cod_producto",item.getKey());
    bundle.putString("nombre_producto",item.getNombre());
    bundle.putString("descripcion_producto",item.getDescripcion());
    bundle.putString("precio_producto",item.getPrecio());
    bundle.putString("imagen_producto",item.getImagen());

    bundle.putString("descuento_producto",item.getPrecio_promo());
    FragmentManager manager = getActivity().getSupportFragmentManager();
    manager.beginTransaction().replace(R.id.container, new FragmentDescripcion()).addToBackStack(null).commit();
    getParentFragmentManager().setFragmentResult("keypromo",bundle);
}
}
