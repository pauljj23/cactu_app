package com.pauljordan.cactuapp.GestorComunitarioFolder;

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
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarEventosActivity;
import com.pauljordan.cactuapp.AdministradorFolder.MainAdminActivity;
import com.pauljordan.cactuapp.AdministradorFolder.RegisterEventoActivity;
import com.pauljordan.cactuapp.LoginActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.ViewEventosActivity;
import com.pauljordan.cactuapp.models.AmenazaAdapter;
import com.pauljordan.cactuapp.models.AmenazaModel;

import java.util.ArrayList;
import java.util.List;

public class MainGestorComunitarioActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    //variables del Navigation Drawer
    private MaterialToolbar toolbarGestor;
    private DrawerLayout drawerLayoutGestor;
    private NavigationView navigationViewGestor;

    //Variables para RecyclerView
    private RecyclerView recyclerViewGestorAmenazas;
    private List<AmenazaModel> amenazaModelList;
    private AmenazaAdapter amenazaAdapter;
    FirebaseDatabase mDatabase;
    private AmenazaModel amenazaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gestor_comunitario);

        mAuth = FirebaseAuth.getInstance();

        toolbarGestor = findViewById(R.id.appBarGestorComunitario);
        drawerLayoutGestor = findViewById(R.id.drawer_layout_gestor_comunitario);
        navigationViewGestor = findViewById(R.id.navigationViewGestorComunitario);

        recyclerViewGestorAmenazas = findViewById(R.id.recyclerViewListarAmenazasGestor);
        recyclerViewGestorAmenazas.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerViewGestorAmenazas.setLayoutManager(new LinearLayoutManager(this));

        //inicializarFirebase();
        mDatabase = FirebaseDatabase.getInstance();

        amenazaModelList = new ArrayList<>();
        amenazaAdapter = new AmenazaAdapter(amenazaModelList);
        recyclerViewGestorAmenazas.setAdapter(amenazaAdapter);
        listarEventosGestor();

        amenazaAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amenazaModel = amenazaModelList.get(recyclerViewGestorAmenazas.getChildAdapterPosition(v));
                Intent intent = new Intent(MainGestorComunitarioActivity.this, ReporteEventoActivity.class);
                intent.putExtra("tipoEvento", amenazaModel);
                startActivity(intent);
            }
        });


        //Accion que realiza al presionar el menu del navigation drawer
        toolbarGestor.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutGestor.openDrawer(GravityCompat.START);
            }
        });
        navigationViewGestor.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayoutGestor.closeDrawer(GravityCompat.START);
                switch (id){
                    //CASE1
                    case R.id.navGestorMensajes:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Mensajes", Toast.LENGTH_SHORT).show();
                        break;

                    //CASE2
                    case R.id.navReportesEnviados:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Reportes enviados", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainGestorComunitarioActivity.this, ReportesEnviadosActivity.class);
                        startActivity(intent);
                        break;

                    //POR IMPLEMETAR
                    //CASE3
                    case R.id.navGestorContactos:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Contactos", Toast.LENGTH_SHORT).show();
                        break;

                    //POR IMPLEMETAR
                    //CASE4
                    case R.id.navGestorInformacion:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Informacion", Toast.LENGTH_SHORT).show();
                        break;

                    //POR IMPLEMETAR
                    //CASE5
                    case R.id.navGestorDesarrollador:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Desarrollador", Toast.LENGTH_SHORT).show();
                        break;
                    //CASE6
                    case R.id.navGestorCerrarSesion:
                        Toast.makeText(MainGestorComunitarioActivity.this, "Cerrar Sesión", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        startActivity(new Intent(MainGestorComunitarioActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }//fin del onCreate

    private void listarEventosGestor() {
        mDatabase.getReference().getRoot().child("Amenazas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                amenazaModelList.removeAll(amenazaModelList);
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AmenazaModel amenazaModel = dataSnapshot.getValue(AmenazaModel.class);
                    amenazaModelList.add(amenazaModel);
                }
                amenazaAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}//fin de la clase


