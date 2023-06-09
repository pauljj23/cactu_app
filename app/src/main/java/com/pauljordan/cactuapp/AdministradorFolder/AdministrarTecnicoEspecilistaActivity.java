package com.pauljordan.cactuapp.AdministradorFolder;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.ViewUserActivity;
import com.pauljordan.cactuapp.models.UsuarioAdapter;
import com.pauljordan.cactuapp.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class AdministrarTecnicoEspecilistaActivity extends AppCompatActivity {

    //Variables para RecyclerView
    private RecyclerView recyclerViewTecnico;
    private List<UsuarioModel> usuariosTE;
    private UsuarioAdapter adapter;
    private DatabaseReference mDatabase;
    private UsuarioModel usuarioTE_Select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_tecnico_especilista);

        recyclerViewTecnico = findViewById(R.id.recyclerViewListarUsuarios);
        recyclerViewTecnico.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerViewTecnico.setLayoutManager(new LinearLayoutManager(this));

        //inicializarFirebase();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        usuariosTE = new ArrayList<>();
        adapter = new UsuarioAdapter(usuariosTE);
        recyclerViewTecnico.setAdapter(adapter);
        listarDatos();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioTE_Select = usuariosTE.get(recyclerViewTecnico.getChildAdapterPosition(v));
                Intent intent = new Intent(AdministrarTecnicoEspecilistaActivity.this, ViewUserActivity.class);
                intent.putExtra("view", usuarioTE_Select);
                startActivity(intent);
                finish();
            }
        });
    }//fin del onCreate

    //Listamos los Datos
    public void listarDatos() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Usuario");
        //antes de listar comparamos que el rol del usuario a listar sea igual a Tecnico especialista
        Query userByRolQuery = mDatabase.orderByChild("rol").equalTo("Tecnico Especialista");
        userByRolQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UsuarioModel usuario = dataSnapshot.getValue(UsuarioModel.class);
                    usuariosTE.add(usuario);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Inflamos el menu del Administrar Gestor Comunitario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Indicamos que accion realiza el icono del menu al presionarlo
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.agregar:
                String rolUsuario = "Tecnico Especialista";
                Toast.makeText(this, "Agregar Usuario", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdministrarTecnicoEspecilistaActivity.this, AdminUsersRegisterActivity.class);
                intent.putExtra("rol",rolUsuario);
                startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}