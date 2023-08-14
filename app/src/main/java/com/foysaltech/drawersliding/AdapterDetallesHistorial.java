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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDetallesHistorial  extends RecyclerView.Adapter<AdapterDetallesHistorial.MyViewHolder> {

    private List<productos_historial> mData;
    private List<Productos> mData2;
    private Context context;

    final AdapterDetallesHistorial.OnItemClickListener listene;
    public AdapterDetallesHistorial(List<productos_historial> mData, List<Productos> mData2,Context context, OnItemClickListener listene) {
        this.mData = mData;
        this.mData2 = mData2;
        this.context = context;
        this.listene = listene;
    }

    public interface OnItemClickListener{
        void onItemClick(productos_historial item);
    }
    @NonNull
    @Override
    public AdapterDetallesHistorial.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_productos_historial,null,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetallesHistorial.MyViewHolder holder, int position) {
       for(int i=0;i < mData2.size();i++){
           holder.bindData(mData.get(position),mData2.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_cantidad_ph,txt_nombre_ph,txt_precio_ph;
        ImageView imagen_ph;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cantidad_ph=itemView.findViewById(R.id.txt_cantidad_ph);
            imagen_ph=itemView.findViewById(R.id.imagen_ph);
            txt_nombre_ph=itemView.findViewById(R.id.txt_nombre_ph);
            txt_precio_ph=itemView.findViewById(R.id.txt_precio_ph);
        }

        void bindData(final productos_historial item, final Productos item2) {




            txt_cantidad_ph.setText("Cantidad: "+item.getCantidad_producto());
            txt_precio_ph.setText(""+item.getSubTotal_producto());

           // Toast.makeText(context.getApplicationContext(), ""+item.getCod_producto(),Toast.LENGTH_LONG).show();
            if(item.getCod_producto().equals(item2.getKey())){
                imagen_ph.setImageBitmap(item2.getImagen2());
                txt_nombre_ph.setText(item2.getNombre());

          //  }else{

         //        imagen_ph.setImageResource(R.drawable.imagen);
           }
        }

    }
}
