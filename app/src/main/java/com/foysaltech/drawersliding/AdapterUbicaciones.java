
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

public class AdapterUbicaciones  extends RecyclerView.Adapter<AdapterUbicaciones.MyViewHolder> {

    private List<Ubicaciones> mData;
    private Context context;

    public AdapterUbicaciones( Context context,List<Ubicaciones> mData, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }

    final AdapterUbicaciones.OnItemClickListener listene;

    public interface OnItemClickListener{
        void onItemClick(Ubicaciones item);
    }


    @NonNull
    @Override
    public AdapterUbicaciones.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_direcciones,null,false);
        return new AdapterUbicaciones.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUbicaciones.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemDescripcion;
        ImageView imagen_direccion;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.nombre_direccion);
            itemDescripcion=itemView.findViewById(R.id.textView2);
            imagen_direccion=itemView.findViewById(R.id.imagen_direccion);

        }
        void bindData(final Ubicaciones item) {

            itemName.setText(item.getNombre_direccion());
            itemDescripcion.setText(item.getCalle1());

            //if (item.getImagen2() != null) {
            //  img.setImageBitmap(item.getImagen2());
            /// }else{

            imagen_direccion.setImageResource(R.drawable.casa);
            //}
            // itemView.findViewById(R.id.botton_promo).setOnClickListener(new View.OnClickListener() {
            //   @Override
            // public void onClick(View v) {
            //   listene.onItemClick(item);
            //  }
            // });

        }
    }
}
