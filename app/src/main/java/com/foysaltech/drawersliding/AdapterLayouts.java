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

public class AdapterLayouts extends RecyclerView.Adapter<AdapterLayouts.MyViewHolder>{
    private Context context;

    public AdapterLayouts(Context context, List<Layouts> mData) {
        this.context = context;
        this.mData = mData;
    }

    private List<Layouts> mData;
    @NonNull
    @Override
    public AdapterLayouts.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_layout,null,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLayouts.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
img=itemView.findViewById(R.id.imagen_layout);
        }

        void bindData(final Layouts item) {
img.setImageResource(item.getImagen());
        }

    }

}
