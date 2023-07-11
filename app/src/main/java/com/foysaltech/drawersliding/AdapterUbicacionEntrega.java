package com.foysaltech.drawersliding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterUbicacionEntrega  extends RecyclerView.Adapter<AdapterUbicacionEntrega.MyViewHolder> {


    private List<Ubicaciones> mData;
    private Context context;

    public AdapterUbicacionEntrega( Context context,List<Ubicaciones> mData, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }
    public void actualizar(List<Ubicaciones> nueva){
        this.mData = nueva;
        notifyDataSetChanged();
    }

    final AdapterUbicacionEntrega.OnItemClickListener listene;

    public interface OnItemClickListener{
        void onItemClick(Ubicaciones item);


    }


    @NonNull
    @Override
    public AdapterUbicacionEntrega.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_direcciones_entrega,null,false);
        return new AdapterUbicacionEntrega.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUbicacionEntrega.MyViewHolder holder, int position) {
        for (int i = 0; i < mData.size(); i++) {

            holder.bindData(mData.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return  1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemDescripcion,txt_contador;
        ImageView imagen_direccion;
        LinearLayout linear_menu;
        ImageView botton_menu_ubi;
        RadioGroup group_ubi_entrega;
        ImageButton botton_editar_ubi,botton_borrar_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            group_ubi_entrega=itemView.findViewById(R.id.group_ubi_entrega);
            itemName=itemView.findViewById(R.id.nombre_direccion_entrega);

        }
        void bindData(final Ubicaciones item) {

            RadioButton rdbtn = new RadioButton(context.getApplicationContext());
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(item.getNombre_direccion());
            rdbtn.setTextColor(Color.GRAY);
            rdbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemClick(item);
                }
            });
            group_ubi_entrega.addView(rdbtn);


        }
    }
}
