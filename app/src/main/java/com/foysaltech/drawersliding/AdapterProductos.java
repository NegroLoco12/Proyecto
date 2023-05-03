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

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.MyViewHolder> {
    private List<Productos> mData;
    private Context context;

    final AdapterProductos.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Productos item);
    }
    public AdapterProductos(Context context, List<Productos> mData,AdapterProductos.OnItemClickListener listener ) {
        this.context = context;
        this.mData = mData;
        this.listene=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_producto,null,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombre_producto;
        TextView descripcion_producto;
        TextView precio_producto;
        ImageView imagen_producto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_producto=itemView.findViewById(R.id.nombre_producto);
            precio_producto=itemView.findViewById(R.id.precio_producto);
           descripcion_producto=itemView.findViewById(R.id.descripcion_producto);
            imagen_producto=(ImageView)itemView.findViewById(R.id.imagen_producto);

        }
        void bindData(final Productos item) {


            precio_producto.setText(item.getPrecio()+" ₲ ");
            nombre_producto.setText(item.getNombre());
            descripcion_producto.setText(item.getDescripcion());
            if (item.getImagen2() != null) {
                imagen_producto.setImageBitmap(item.getImagen2());
            }else{

                imagen_producto.setImageResource(R.drawable.imagen);
            }
            itemView.findViewById(R.id.botton_producto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemClick(item);
                }
            });

        }
    }


}
