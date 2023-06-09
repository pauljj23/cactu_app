package com.pauljordan.cactuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pauljordan.cactuapp.AdministradorFolder.RegisterAdminActivity;

public class AuthOptionsActivity extends AppCompatActivity {
    //variables
    TextView gotoAdministradorRegister, gotoGestorComunitarioRegister, gotoTecnicoEspecilistaRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_options);


        gotoAdministradorRegister = findViewById(R.id.administradorAuthOptionTextView);
        gotoGestorComunitarioRegister = findViewById(R.id.gestorComunitarioAuthOptionTextView);
        gotoTecnicoEspecilistaRegister = findViewById(R.id.tecnicoEspecilistaAuthOptionTextView);


        //Cuando el usuario seleciona el Rol Administrador
        gotoAdministradorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipoUsuario = "Administrador";
                Intent intent = new Intent(AuthOptionsActivity.this, RegisterAdminActivity.class);
                intent.putExtra("rol",tipoUsuario);
                startActivity(intent);
                finish();

            }
        });

        //Cuando el usuario selecciona el Rol Gestor Comunitario
        gotoGestorComunitarioRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardamos en una variable el tipo de usuari seleccionado
                String tipoUsuario = "Gestor Comunitario";
                Intent intent = new Intent(AuthOptionsActivity.this, RegisterActivity.class);
                intent.putExtra("rol",tipoUsuario);
                startActivity(intent);
                finish();

            }
        });

        //CUando el usuario selecciona el Rol Tecnico Especialista
        gotoTecnicoEspecilistaRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardamos en una variable el tipo de usuari seleccionado
                String tipoUsuario = "Tecnico Especialista";
                Intent intent = new Intent(AuthOptionsActivity.this, RegisterActivity.class);
                intent.putExtra("rol",tipoUsuario);
                startActivity(intent);
                finish();

            }
        });
    }

    //Cuando el usuario presione el boton back, regresa al Login - LoginActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AuthOptionsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}