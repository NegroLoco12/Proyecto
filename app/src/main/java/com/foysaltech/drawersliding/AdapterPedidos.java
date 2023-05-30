package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolderDatos> {
    private List<Pedidos> mData;

    private LayoutInflater mInflater;
    private Context context;
    final AdapterPedidos.OnItemClickListener listener;

    public AdapterPedidos(List<Pedidos> mData, Context context, AdapterPedidos.OnItemClickListener listener) {
        this.mData = mData;

        this.context = context;
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(Pedidos item);
    }

    @NonNull
    @Override
    public AdapterPedidos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_pedidos,null,false);
        return new AdapterPedidos.ViewHolderDatos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPedidos.ViewHolderDatos holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView txt_nombre_pedido;
        TextView txt_descripcion_pedido;
        TextView txt_precio_pedido;
        TextView txt_cantidad_pedido;
        TextView txt_id_pedido;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txt_nombre_pedido=(TextView)itemView.findViewById(R.id.txt_nombre_pedido);
           // txt_descripcion_pedido=(TextView)itemView.findViewById(R.id.txt_descripcion_pedido);
            txt_precio_pedido=(TextView)itemView.findViewById(R.id.txt_precio_pedido);
            txt_cantidad_pedido=(TextView)itemView.findViewById(R.id.txt_cantidad_pedido);


        }
        void bindData(final Pedidos item) {
           // int precio=item.getPrecio_total();
         //   DecimalFormat formatea = new DecimalFormat("###,###.##");
            txt_nombre_pedido.setText(item.getNombre()+"");
      //      txt_descripcion_pedido.setText(item.getDescripcion());
          //  txt_precio_pedido.setText(formatea.format(precio) + " ₲");
          //  txt_cantidad_pedido.setText(item.getCantidad()+" ");
////////////////////////////////////////////////////////////////////////////////////////////////////


        }
    }
}
