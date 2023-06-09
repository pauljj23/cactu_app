package com.pauljordan.cactuapp.AdministradorFolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pauljordan.cactuapp.LoginActivity;
import com.pauljordan.cactuapp.R;

public class MainAdminActivity extends AppCompatActivity {
    private MaterialButton cerrarSesionAdminButton;
    //variables de Firebase
    private FirebaseAuth mAuth;

    //variables de Navigation Drawer
    private MaterialToolbar toolbarAdmin;
    private DrawerLayout drawerLayoutAdmin;
    private NavigationView navigationViewAdmin;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mAuth = FirebaseAuth.getInstance();

                                    //id del activity_main_admin
        toolbarAdmin = findViewById(R.id.appBarAdmin);
        drawerLayoutAdmin = findViewById(R.id.drawer_layout_admin);
        navigationViewAdmin = findViewById(R.id.navigationViewAdmin);

        //Accion que realiza al presionar el menu del navigation drawer
        toolbarAdmin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutAdmin.openDrawer(GravityCompat.START);
            }
        });

        //Accion que se realiza al selecionar cada uno de los items del menu
        navigationViewAdmin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayoutAdmin.closeDrawer(GravityCompat.START);
                switch(id)
                {
                    case R.id.navAdminMensajes:
                        Toast.makeText(MainAdminActivity.this, "Mensajes", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navAdminGestorComunitario:
                        Toast.makeText(MainAdminActivity.this, "Gestor Comunitario", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainAdminActivity.this, AdministrarGestorComunitarioActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navAdminTecnicoEspecialista:
                        Toast.makeText(MainAdminActivity.this, "Tecnico Especialista", Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainAdminActivity.this, AdministrarTecnicoEspecilistaActivity.class);
                        startActivity(intent);
                        break;
                        //NOTA: Solo puede existir un usuario administrador
                    /*case R.id.navAdminUsuarioAdministrado:
                        Toast.makeText(MainAdminActivity.this, "Administrador", Toast.LENGTH_SHORT).show();
                        break;*/
                    case R.id.navAdminEventos:
                        Toast.makeText(MainAdminActivity.this, "Amenazas", Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainAdminActivity.this,AdministrarEventosActivity.class);
                        startActivity(intent);
                        break;
                        //POR IMPLEMENTAR
                    case R.id.navAdminInformacion:
                        Toast.makeText(MainAdminActivity.this, "Informacion", Toast.LENGTH_SHORT).show();
                        break;
                        //POR IMPLEMENTAR
                    case R.id.navAdminDesarrollador:
                        Toast.makeText(MainAdminActivity.this, "Desarrollador", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.navAdminCerrarSesion:
                        mAuth.signOut();
                        startActivity(new Intent(MainAdminActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:
                        return true;
                }//fin del switch
                return true;
            }
        });//fin del setNavigationItemSelectedListener

    }//fin del onCreate

}//fin del Activity