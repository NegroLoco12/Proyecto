package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMetodoPago extends RecyclerView.Adapter<AdapterMetodoPago.MyViewHolder> {

    private Context context;
    final AdapterMetodoPago.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClickPos();
        void onItemClickEfectivo();
        void onItemClickOline();
    }
    public AdapterMetodoPago(Context context,  AdapterMetodoPago.OnItemClickListener listener ) {
        this.context = context;

        this.listene=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_metodopago,null,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.bindData();
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        RadioButton radio_online,radio_efectivo,radio_pos;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        radio_online=itemView.findViewById(R.id.radio_online);

        radio_efectivo=itemView.findViewById(R.id.radio_efectivo);

        radio_pos=itemView.findViewById(R.id.radio_pos);
    }
        void bindData() {
        radio_online.setOnClickListener(new View.OnClickListener() {
         @Override
             public void onClick(View view) {
                 listene.onItemClickOline();
     }
            });
            radio_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listene.onItemClickPos();
                }
            });
            radio_efectivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listene.onItemClickEfectivo();
                }
            });




        }
    }

}
