package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.MyViewHolder> {
    private List<Historial> mData;
    private Context context;

    public AdapterHistorial(List<Historial> mData, Context context, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }

    final AdapterHistorial.OnItemClickListener listene;
    public interface OnItemClickListener{
        void onItemClick(Historial item, int posi);
    }
    @NonNull
    @Override
    public AdapterHistorial.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_historial,null,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorial.MyViewHolder holder, int position) {
        holder.bindData(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_fecha, txt_nro,txt_estado,txt_total;
        private ImageView imagen_historial;
private CardView cardViewHistorial;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen_historial=itemView.findViewById(R.id.imagen_historial);
            txt_fecha=itemView.findViewById(R.id.txt_fecha_historial);
            txt_nro=itemView.findViewById(R.id.txt_nro_historial);
            txt_estado=itemView.findViewById(R.id.txt_estado_historial);
            txt_total=itemView.findViewById(R.id.txt_total_historial);

            cardViewHistorial=itemView.findViewById(R.id.cardViewHistorial);
        }
        void bindData(final Historial item,int posi) {
            imagen_historial.setImageResource(R.drawable.ic_cart_outline_grey600_24dp);
            txt_nro.setText("Número de Pedido: "+(posi+1));
            txt_fecha.setText("Fecha del pedido: "+item.getFecha()+" "+item.getHora());
            txt_total.setText("Total del pedido: "+item.getTotal());
            txt_estado.setText("Estado: ");
            cardViewHistorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listene.onItemClick(item,posi);

                }
            });
        }
    }
}
