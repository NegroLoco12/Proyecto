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
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemDescripcion,txt_contador;
        ImageView imagen_direccion;
        LinearLayout linear_menu;
        ImageView botton_menu_ubi;
        ImageButton botton_editar_ubi,botton_borrar_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.nombre_direccion_entrega);

        }
        void bindData(final Ubicaciones item) {

            itemName.setText(item.getNombre_direccion());


        }
    }
}
