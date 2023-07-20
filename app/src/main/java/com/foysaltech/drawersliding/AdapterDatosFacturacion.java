package com.foysaltech.drawersliding;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDatosFacturacion extends RecyclerView.Adapter<AdapterDatosFacturacion.MyViewHolder> {
    private List<Contribuyentes> mData;
    private Context context;
    final AdapterDatosFacturacion.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Contribuyentes item);
    }
    public AdapterDatosFacturacion(Context context, List<Contribuyentes> mData, AdapterDatosFacturacion.OnItemClickListener listener ) {
        this.context = context;
        this.mData = mData;
        this.listene=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_datosfactura,null,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     for (int i = 0; i < mData.size(); i++) {

            holder.bindData(mData.get(i));
    }

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{

        RadioGroup group_entrega;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        group_entrega=itemView.findViewById(R.id.group_factura);
    }
        //for (int i = 1; i <= 2; i++) {
        void bindData(final Contribuyentes item) {
             group_entrega.setOrientation(LinearLayout.VERTICAL);
            //
            //for (int i = 1; i <= 2; i++) {
                RadioButton rdbtn = new RadioButton(context.getApplicationContext());
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(item.getRazon_social());
                rdbtn.setTextColor(Color.GRAY);
                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listene.onItemClick(item);
                    }
                });
                group_entrega.addView(rdbtn);
           // }

            }

        }
    }


