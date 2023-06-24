
package com.foysaltech.drawersliding;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class FragmentDetallePedido extends Fragment {


    private List<MetodoEntrega> elements_metodo;
   private RecyclerView contenedorMetodoEntrega;
   private AdapterMetodoEntrega listAdapterMedodo;
   private ImageView imageView1;
   private CardView cardView1;
   int a=0;

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
        imageView1=view.findViewById(R.id.imageView1);
        cardView1=view.findViewById(R.id.cardView1);
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
}