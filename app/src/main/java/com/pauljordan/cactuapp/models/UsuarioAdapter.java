package com.pauljordan.cactuapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pauljordan.cactuapp.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.PeronasViewHolder> implements View.OnClickListener
    {
    //creamos una lista
     private List<UsuarioModel> usuarios;
     private View.OnClickListener listener;
     //private RecyclerItemClick itemClick;
     //Context
     Context context;

    //constructor de la clase
    public UsuarioAdapter(List<UsuarioModel> usuarios)
    {
        this.usuarios = usuarios;

    }

    //Metodos Implementados
    @NonNull
    @Override
    public PeronasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_usuarios,parent,false);
        PeronasViewHolder holder = new PeronasViewHolder(view);

        //vista del SetOnCLickListener
        view.setOnClickListener(UsuarioAdapter.this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PeronasViewHolder holder, int position) {
        UsuarioModel u = usuarios.get(position);
        holder.textViewUsuario.setText(u.getNombre());
        holder.textViewRol.setText(u.getRol());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.ItemClick(u);
            }
        });*/
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ViewUserActivity.class);

                intent.putExtra("itemDetail", u);
                holder.itemView.getContext().startActivity(intent);

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void onClick(View v){
        if (listener!=null){
            listener.onClick(v);

        }
    }

     public class PeronasViewHolder extends RecyclerView.ViewHolder{
        //declaramos las variables que se enlazaran con las vistas
        private TextView textViewUsuario, textViewRol;
        //Construcctor matchin super de la clase PersonasViewHolder
        public PeronasViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            textViewUsuario = itemView.findViewById(R.id.rUsuario);
            textViewRol = itemView.findViewById(R.id.rRol);

        }
    }

    /*public interface RecyclerItemClick {
        void ItemClick(UsuarioModel item);
    }*/
}
