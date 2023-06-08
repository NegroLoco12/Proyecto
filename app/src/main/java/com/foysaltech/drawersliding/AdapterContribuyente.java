package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterContribuyente extends RecyclerView.Adapter<AdapterContribuyente.MyViewHolder> {
    private List<Contribuyentes> mData;

    private Context context;
    final AdapterContribuyente.OnItemClickListener listene;

    public AdapterContribuyente(List<Contribuyentes> mData, Context context, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }
    public interface OnItemClickListener{
        void onItemClick(Contribuyentes item);
    }

    @NonNull
    @Override
    public AdapterContribuyente.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContribuyente.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount(){
        return mData.size();
}
    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item_nombre,item_ruc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_nombre=itemView.findViewById(R.id.nombre_contri);
            item_ruc=itemView.findViewById(R.id.txt_nro_contri);
        }
        void bindData(final Contribuyentes item) {

        }
    }

}
