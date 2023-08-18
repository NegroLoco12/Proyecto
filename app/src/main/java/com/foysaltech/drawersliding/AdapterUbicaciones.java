
package com.foysaltech.drawersliding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Handler;
public class AdapterUbicaciones  extends RecyclerView.Adapter<AdapterUbicaciones.MyViewHolder> {


    private List<Ubicaciones> mData;
    private Context context;

    public AdapterUbicaciones( Context context,List<Ubicaciones> mData, OnItemClickListener listene) {
        this.mData = mData;
        this.context = context;
        this.listene = listene;
    }
    public void actualizar(List<Ubicaciones> nueva){
        this.mData = nueva;
        notifyDataSetChanged();
    }

    final AdapterUbicaciones.OnItemClickListener listene;

    public interface OnItemClickListener{
        void onItemClick(Ubicaciones item);
        void onItemClick2(Ubicaciones item);

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
        TextView itemName,itemDescripcion,txt_contador;
        ImageView imagen_direccion;
        LinearLayout linear_menu;
        ImageView botton_menu_ubi;
        ImageButton botton_editar_ubi,botton_borrar_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_contador=itemView.findViewById(R.id.txt_contador);
            itemName=itemView.findViewById(R.id.nombre_direccion);
            itemDescripcion=itemView.findViewById(R.id.textView2);
            imagen_direccion=itemView.findViewById(R.id.imagen_direccion);
            linear_menu=itemView.findViewById(R.id.linear_menu);
            botton_menu_ubi=itemView.findViewById(R.id.menu_ubi);
            botton_borrar_menu=itemView.findViewById(R.id.botton_borrar_menu);
            botton_editar_ubi=itemView.findViewById(R.id.botton_editar_ubi);
        }
        void bindData(final Ubicaciones item) {

            itemName.setText(item.getNombre_direccion());
            itemDescripcion.setText(item.getCalle1());

            if(item.icono==0){
                imagen_direccion.setImageResource(R.drawable.casa);

            } else if (item.icono==1) {
                imagen_direccion.setImageResource(R.drawable.corazon);

            } else if (item.icono==2) {
                imagen_direccion.setImageResource(R.drawable.maleta);

            } else if (item.icono==3) {
                imagen_direccion.setImageResource(R.drawable.etiqueta);

            }

            botton_menu_ubi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a=txt_contador.getText().toString();
                    if(a.equals("0")){
                        linear_menu.setVisibility(View.VISIBLE);
                        txt_contador.setText("1");
                    }else{
                        txt_contador.setText("0");
                        linear_menu.setVisibility(View.GONE);

                    }
                }
            });
            PorterDuffColorFilter redFilter = new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            botton_borrar_menu.getBackground().setColorFilter(redFilter);

            botton_borrar_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PorterDuffColorFilter redFilter = new PorterDuffColorFilter(Color.rgb(255,192,192), PorterDuff.Mode.MULTIPLY);
                    botton_borrar_menu.getBackground().setColorFilter(redFilter);
                    listene.onItemClick(item);

                }
            });
            botton_editar_ubi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemClick2(item);
                }
            });
        }
    }
}
