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

public class AdapterMetodoEntrega extends RecyclerView.Adapter<AdapterMetodoEntrega.MyViewHolder> {
    private List<MetodoEntrega> mData;
    private Context context;
    final AdapterMetodoEntrega.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Categorias item);
    }
    public AdapterMetodoEntrega(Context context, List<MetodoEntrega> mData, AdapterMetodoEntrega.OnItemClickListener listener ) {
        this.context = context;
        this.mData = mData;
        this.listene=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_entrega,null,false);
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
        TextView itemName;
        ImageView img;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName=itemView.findViewById(R.id.nombre_metodo_entrega);
        img=itemView.findViewById(R.id.imagen_metodo);

    }
        void bindData(final MetodoEntrega item) {
            itemName.setText(item.getNombre());
            img.setImageResource(R.drawable.entrega);

        }
    }

}
