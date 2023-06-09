package com.pauljordan.cactuapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.pauljordan.cactuapp.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class AmenazaAdapter extends RecyclerView.Adapter<AmenazaAdapter.AmenazasViewHolder> implements View.OnClickListener {

    //creamos una lista
    private List<AmenazaModel> amenazas;
    private View.OnClickListener amenazaslistener;
    //context
    Context amenazasContext;

    public AmenazaAdapter(List<AmenazaModel> amenazas) {
        this.amenazas = amenazas;
    }

    //Metodos
    @NonNull
    @Override
    public AmenazasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_amenazas,parent,false);
        AmenazasViewHolder holder = new AmenazasViewHolder(view);

        //vista del SetOnCLickListener
        view.setOnClickListener(AmenazaAdapter.this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AmenazasViewHolder holder, int position) {
        AmenazaModel amenazaModel = amenazas.get(position);
        holder.textViewAmenazas.setText(amenazaModel.getAmenaza());
    }

    @Override
    public int getItemCount() {
        return amenazas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.amenazaslistener = listener;
    }

    @Override
    public void onClick(View v) {
        if (amenazaslistener!=null){
            amenazaslistener.onClick(v);

        }
    }

    public class AmenazasViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAmenazas;
        public AmenazasViewHolder(@NonNull View itemView) {
            super(itemView);
            amenazasContext = itemView.getContext();
            textViewAmenazas = itemView.findViewById(R.id.rAmenaza);
        }
    }
}
