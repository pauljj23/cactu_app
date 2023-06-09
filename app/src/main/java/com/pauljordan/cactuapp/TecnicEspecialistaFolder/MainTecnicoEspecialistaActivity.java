package com.pauljordan.cactuapp.TecnicEspecialistaFolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pauljordan.cactuapp.GestorComunitarioFolder.MainGestorComunitarioActivity;
import com.pauljordan.cactuapp.GestorComunitarioFolder.ReportesEnviadosActivity;
import com.pauljordan.cactuapp.LoginActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.ViewReportesEnviadosActivity;
import com.pauljordan.cactuapp.models.ReporteEventoAdapter;
import com.pauljordan.cactuapp.models.ReporteEventoModel;

import java.util.ArrayList;
import java.util.List;

public class MainTecnicoEspecialistaActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //variables del Navigation Drawer
    private MaterialToolbar toolbarTecnico;
    private DrawerLayout drawerLayoutTecnico;
    private NavigationView navigationViewTecnico;

    //Variables para RecyclerView
    private RecyclerView recyclerViewEventosReportados;
    private List<ReporteEventoModel> reporteEventoModelList;
    private ReporteEventoAdapter reporteEventoAdapter;
    FirebaseDatabase mDatabase;
    private ReporteEventoModel reporteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tecnico_especialista);

        mAuth = FirebaseAuth.getInstance();

        toolbarTecnico = findViewById(R.id.appBarTecnicoEspecialista);
        drawerLayoutTecnico = findViewById(R.id.drawer_layout_tecnico_especialista);
        navigationViewTecnico = findViewById(R.id.navigationViewTecnicoEspecialista);

        recyclerViewEventosReportados = findViewById(R.id.recyclerViewListareventosReportados);
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
                Intent intent = new Intent(MainTecnicoEspecialistaActivity.this, ViewReportesEnviadosActivity.class);
                intent.putExtra("viewReportesEnviados", reporteModel);
                startActivity(intent);
            }
        });

        //Accion que realiza al presionar el menu del navigation drawer
        toolbarTecnico.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutTecnico.openDrawer(GravityCompat.START);
            }
        });

        navigationViewTecnico.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayoutTecnico.closeDrawer(GravityCompat.START);
                switch (id){
                    //CASE1
                    case R.id.navTecnicoMensajes:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Mensajes", Toast.LENGTH_SHORT).show();
                        break;

                    //CASE2
                    case R.id.navReportesRecibidos:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Reportes recibidos", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(MainTecnicoEspecialistaActivity.this, ReportesEnviadosActivity.class);
                        startActivity(intent);
                        break;*/

                    //POR IMPLEMETAR
                    //CASE3
                    case R.id.navTecnicoContactos:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Contactos", Toast.LENGTH_SHORT).show();
                        break;

                    //POR IMPLEMETAR
                    //CASE4
                    case R.id.navTecnicoInformacion:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Informacion", Toast.LENGTH_SHORT).show();
                        break;

                    //POR IMPLEMETAR
                    //CASE5
                    case R.id.navTecnicoDesarrollador:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Desarrollador", Toast.LENGTH_SHORT).show();
                        break;
                    //CASE6
                    case R.id.navTecnicoCerrarSesion:
                        Toast.makeText(MainTecnicoEspecialistaActivity.this, "Cerrar Sesión", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(MainTecnicoEspecialistaActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }//fin del onCrrate

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

}//fin de la clase