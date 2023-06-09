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

public class ReporteEventoAdapter extends RecyclerView.Adapter<ReporteEventoAdapter.ReporteViewHolder> implements View.OnClickListener {

    private List<ReporteEventoModel> reporteEventos;
    private View.OnClickListener reportEventosListener;
    Context reporteContext;

    public ReporteEventoAdapter(List<ReporteEventoModel> reporteEventos) {
        this.reporteEventos = reporteEventos;
    }

    @NonNull
    @Override

    public ReporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_reporte_eventos,parent,false);
        ReporteViewHolder holder = new ReporteViewHolder(view);

        //vista del SetOnCLickListener
        view.setOnClickListener(ReporteEventoAdapter.this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteViewHolder holder, int position) {
        ReporteEventoModel reporteEventoModel = reporteEventos.get(position);
        holder.textViewNombre.setText(reporteEventoModel.getNombre());
        holder.textViewEvento.setText(reporteEventoModel.getEvento());
        holder.textViewFecha.setText(reporteEventoModel.getFecha());
        holder.textViewParroquia.setText(reporteEventoModel.getParroquia());
        holder.textViewComunidad.setText(reporteEventoModel.getComunidad());
    }

    @Override
    public int getItemCount() {
        return reporteEventos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.reportEventosListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (reportEventosListener!=null){
            reportEventosListener.onClick(v);

        }
    }

    public class ReporteViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre, textViewEvento, textViewFecha, textViewParroquia, textViewComunidad;

        public ReporteViewHolder(@NonNull View itemView) {
            super(itemView);
            reporteContext = itemView.getContext();
            textViewNombre = itemView.findViewById(R.id.eventoReportadoNombre);
            textViewEvento = itemView.findViewById(R.id.eventoReportadoEvento);
            textViewFecha = itemView.findViewById(R.id.eventoReportadoFecha);
            textViewParroquia = itemView.findViewById(R.id.eventoReportadoParroquia);
            textViewComunidad = itemView.findViewById(R.id.eventoReportadoComunidad);
        }
    }
}
