package com.pauljordan.cactuapp.AdministradorFolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.ViewEventosActivity;
import com.pauljordan.cactuapp.models.AmenazaAdapter;
import com.pauljordan.cactuapp.models.AmenazaModel;
import com.pauljordan.cactuapp.models.UsuarioAdapter;
import com.pauljordan.cactuapp.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class AdministrarEventosActivity extends AppCompatActivity {

    //Variables para RecyclerView
    private RecyclerView recyclerViewEventos;
    private List<AmenazaModel> amenazaModelList;
    private AmenazaAdapter amenazaAdapter;
    FirebaseDatabase mDatabase;
    private AmenazaModel amenazaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_eventos);

        recyclerViewEventos = findViewById(R.id.recyclerViewListarAmenazas);
        recyclerViewEventos.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(this));

        //inicializarFirebase();
        mDatabase = FirebaseDatabase.getInstance();

        amenazaModelList = new ArrayList<>();
        amenazaAdapter = new AmenazaAdapter(amenazaModelList);
        recyclerViewEventos.setAdapter(amenazaAdapter);
        listarEventos();
        amenazaAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amenazaModel = amenazaModelList.get(recyclerViewEventos.getChildAdapterPosition(v));
                Intent intent = new Intent(AdministrarEventosActivity.this, ViewEventosActivity.class);
                intent.putExtra("viewEvento", amenazaModel);
                startActivity(intent);
                finish();
            }
        });
    }//fin del onCreate
    //*********************************************************************************************************************
    //Inflamos el menu del Administrar Gestor Comunitario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_evento_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Indicamos que accion realiza el icono del menu al presionarlo
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.agregarEvento:
                Toast.makeText(this, "Agregar Usuario", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdministrarEventosActivity.this, RegisterEventoActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }//fin del onOptionsItemSelected

    //Listar eventos
    private void listarEventos() {
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