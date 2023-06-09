package com.pauljordan.cactuapp.GestorComunitarioFolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarEventosActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.ViewEventosActivity;
import com.pauljordan.cactuapp.ViewReportesEnviadosActivity;
import com.pauljordan.cactuapp.models.AmenazaAdapter;
import com.pauljordan.cactuapp.models.AmenazaModel;
import com.pauljordan.cactuapp.models.ReporteEventoAdapter;
import com.pauljordan.cactuapp.models.ReporteEventoModel;

import java.util.ArrayList;
import java.util.List;

public class ReportesEnviadosActivity extends AppCompatActivity {

    //Variables para RecyclerView
    private RecyclerView recyclerViewEventosReportados;

    private List<ReporteEventoModel> reporteEventoModelList;
    private ReporteEventoAdapter reporteEventoAdapter;
    FirebaseDatabase mDatabase;
    private ReporteEventoModel reporteModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_enviados);

        recyclerViewEventosReportados = findViewById(R.id.recyclerViewListarEventosReportados);
        recyclerViewEventosReportados.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerViewEventosReportados.setLayoutManager(new LinearLayoutManager(this));

        //inicializarFirebase();
        mDatabase = FirebaseDatabase.getInstance();

        reporteEventoModelList = new ArrayList<>();
        reporteEventoAdapter = new ReporteEventoAdapter(reporteEventoModelList);
        recyclerViewEventosReportados.setAdapter(reporteEventoAdapter);
        listarEventosReportados();
        reporteEventoAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reporteModel = reporteEventoModelList.get(recyclerViewEventosReportados.getChildAdapterPosition(v));
                Intent intent = new Intent(ReportesEnviadosActivity.this, ViewReportesEnviadosActivity.class);
                intent.putExtra("viewReportesEnviados", reporteModel);
                startActivity(intent);
                finish();
            }
        });
    }

    private void listarEventosReportados() {
        mDatabase.getReference().getRoot().child("Reporte_Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reporteEventoModelList.removeAll(reporteEventoModelList);
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ReporteEventoModel eventosreportModel = dataSnapshot.getValue(ReporteEventoModel.class);
                    reporteEventoModelList.add(eventosreportModel);
                }
                reporteEventoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}