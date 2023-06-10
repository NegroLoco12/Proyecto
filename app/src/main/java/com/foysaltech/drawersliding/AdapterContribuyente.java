package com.foysaltech.drawersliding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        void onItemClick2(Contribuyentes item);

    }

    @NonNull
    @Override
    public AdapterContribuyente.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_contribuyentes,null,false);
        return new AdapterContribuyente.MyViewHolder(v);
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
        TextView item_nombre,item_ruc,txt_contador;
        Button botton_borrar_menu,botton_editar_contri,botton_menu_contri;
        LinearLayout linear_menu_contri;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_nombre=itemView.findViewById(R.id.nombre_contri);
            item_ruc=itemView.findViewById(R.id.txt_nro_contri);
            botton_borrar_menu=itemView.findViewById(R.id.botton_borrar_contri);
            botton_editar_contri=itemView.findViewById(R.id.botton_editar_contri);
            txt_contador=itemView.findViewById(R.id.txt_contador);
            linear_menu_contri=itemView.findViewById(R.id.linear_menu_contri);
            botton_menu_contri=itemView.findViewById(R.id.menu_contri);
        }
        void bindData(final Contribuyentes item) {
            item_nombre.setText(item.getRazon_social());
            item_ruc.setText(item.getDocumento());
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
            botton_editar_contri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemClick2(item);
                }
            });

            botton_menu_contri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a=txt_contador.getText().toString();
                    if(a.equals("0")){
                        linear_menu_contri.setVisibility(View.VISIBLE);
                        txt_contador.setText("1");
                    }else{
                        txt_contador.setText("0");
                        linear_menu_contri.setVisibility(View.GONE);

                    }
                }
            });
        }
    }

}
