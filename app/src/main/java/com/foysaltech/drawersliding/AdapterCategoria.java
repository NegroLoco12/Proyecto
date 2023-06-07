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

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {
    private List<Categorias> mData;
    private Context context;

    final AdapterCategoria.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Categorias item);
    }
    public AdapterCategoria(Context context, List<Categorias> mData,AdapterCategoria.OnItemClickListener listener ) {
        this.context = context;
        this.mData = mData;
        this.listene=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.menu_item,null,false);
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
        itemName=itemView.findViewById(R.id.ItemName);
        img=(ImageView)itemView.findViewById(R.id.imagen_promo);

    }
        void bindData(final Categorias item) {



            itemName.setText(item.getDescripcion());
            if (item.getImagen2() != null) {
                img.setImageBitmap(item.getImagen2());
            }else{

            img.setImageResource(R.drawable.imagen);
            }
            itemView.findViewById(R.id.imagen_promo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemClick(item);
                }
            });

        }
    }

}
