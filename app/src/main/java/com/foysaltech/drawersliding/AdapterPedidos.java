package com.foysaltech.drawersliding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolderDatos> {
    private List<Pedidos> mData;

    private LayoutInflater mInflater;
     Context context;
    final AdapterPedidos.OnItemClickListener listener;
    final AdapterPedidos.OnItemClickListener listener2;
    final AdapterPedidos.OnItemClickListener listener3;

    public AdapterPedidos(List<Pedidos> mData, Context context, AdapterPedidos.OnItemClickListener listener, AdapterPedidos.OnItemClickListener listener2, AdapterPedidos.OnItemClickListener listener3) {
        this.mData = mData;

        this.context = context;
        this.listener=listener;
        this.listener2=listener2;
        this.listener3=listener3;
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
        TextView txt_descripcion_pedido,txt_descuento_pedido;
        TextView txt_precio_pedido,txt_precio_inicial;
        TextView txt_cantidad_pedido;
        TextView txt_id_pedido;
        ImageView imagen;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txt_nombre_pedido=(TextView)itemView.findViewById(R.id.txt_nombre_pedido);
           // txt_descripcion_pedido=(TextView)itemView.findViewById(R.id.txt_descripcion_pedido);
            txt_precio_pedido=(TextView)itemView.findViewById(R.id.txt_precio_pedido);
            txt_cantidad_pedido=(TextView)itemView.findViewById(R.id.txt_cantidad_pedido);
            imagen=(ImageView) itemView.findViewById(R.id.imagen_pedido);
            txt_precio_inicial=(TextView)itemView.findViewById(R.id.txt_pedido_precio_inicial);
            txt_descuento_pedido=(TextView)itemView.findViewById(R.id.txt_descuento_pedido);
        }
        void bindData(final Pedidos item) {
            int precio=item.getPrecio_total();
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            txt_nombre_pedido.setText(item.getNombre()+"");
            txt_descuento_pedido.setText(item.getPrecio_real()+"");
            txt_precio_pedido.setText("Total ₲ "+formatea.format(precio)  );
            txt_cantidad_pedido.setText(item.getCantidad()+" ");
            imagen.setImageBitmap(item.getImagen());
            txt_precio_inicial.setText(" ₲ "+item.getPrecio_inicial());
       //     if(item.getPrecio_descuento()==0){
             txt_descuento_pedido.setVisibility(View.VISIBLE);
           // }
           // Toast.makeText(context, item.getPrecio_inicial()+"", Toast.LENGTH_SHORT).show();
////////////////////////////////////////////////////////////////////////////////////////////////////

       if(item.getCantidad()==1){
           itemView.findViewById(R.id.botton_borrar_item).setVisibility(View.VISIBLE);
           itemView.findViewById(R.id.botton_disminuir_pedido).setVisibility(View.GONE);
       }else{
           itemView.findViewById(R.id.botton_disminuir_pedido).setVisibility(View.VISIBLE);
           itemView.findViewById(R.id.botton_borrar_item).setVisibility(View.GONE);
       }
            itemView.findViewById(R.id.botton_aumentar_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick(item);

                }
            });
            itemView.findViewById(R.id.botton_borrar_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            itemView.findViewById(R.id.botton_disminuir_pedido).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener3.onItemClick(item);
                }
            });
        }
    }
}
