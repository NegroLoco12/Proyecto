package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterPromo extends RecyclerView.Adapter<AdapterPromo.MyViewHolder> {
    private List<Productos> mData;

    public AdapterPromo(List<Productos> mData, Context context, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }

    private Context context;

    final AdapterPromo.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Productos item);
    }
    @NonNull
    @Override
    public AdapterPromo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.menu_promo,null,false);
        return new AdapterPromo.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPromo.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombre_producto;
        TextView descripcion_producto;
        TextView precio_producto, descripcion_promo;
        TextView precio_producto_promo;
        ImageView imagen_producto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_producto=itemView.findViewById(R.id.txt_nombre_promo);
            precio_producto=itemView.findViewById(R.id.txt_precio_promo);
            imagen_producto=(ImageView)itemView.findViewById(R.id.imagen_promo);
            precio_producto_promo=itemView.findViewById(R.id.txt_precio_actual_promo);
            descripcion_promo=itemView.findViewById(R.id.txt_descripcion_promo);
        }
        void bindData(final Productos item) {
            descripcion_promo.setText(item.getDescripcion());
            precio_producto_promo.setText(item.getPrecio_promo()+" ₲ ");
            precio_producto.setText(item.getPrecio()+" ₲ ");
            nombre_producto.setText(item.getNombre());
//            descripcion_producto.setText(item.getDescripcion());
            if (item.getImagen2() != null) {
                imagen_producto.setImageBitmap(item.getImagen2());
            }else{

                imagen_producto.setImageResource(R.drawable.imagen);
            }
           // itemView.findViewById(R.id.botton_producto).setOnClickListener(new View.OnClickListener() {
          //      @Override
           //     public void onClick(View v) {
           //         listene.onItemClick(item);
           //     }
           // });

        }
    }


}
