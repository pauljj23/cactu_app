package com.pauljordan.cactuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pauljordan.cactuapp.AdministradorFolder.MainAdminActivity;
import com.pauljordan.cactuapp.GestorComunitarioFolder.MainGestorComunitarioActivity;
import com.pauljordan.cactuapp.TecnicEspecialistaFolder.MainTecnicoEspecialistaActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //codigo para ver la ventana principal full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Verificamos si hay algun usuario ya loggeado, si es asi ingesamos directo al perfil del UsuarioModel
        if (mAuth.getCurrentUser()!=null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mDatabase.child("Usuario").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                     String loggedUser = snapshot.child("rol").getValue().toString();

                                    if (loggedUser.equals("Administrador")){
                                        Intent intent = new Intent(SplashScreenActivity.this, MainAdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } //fin del if equals Administrador
                                    else if (loggedUser.equals("Gestor Comunitario")){
                                        Intent intent = new Intent(SplashScreenActivity.this, MainGestorComunitarioActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } //fin del if equals Gestor Comunitario
                                    else if (loggedUser.equals("Tecnico Especialista")) {
                                        Intent intent = new Intent(SplashScreenActivity.this, MainTecnicoEspecialistaActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }//fin del if equals Tecnico Especialista
                                    /*else{
                                        Toast.makeText(LoginActivity.this, "UsuarioModel no registrado", Toast.LENGTH_SHORT).show();
                                    }*/
                                }// fin del if (snapshot.exists())
                            }//fin del onDataChange
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });//fin del addValueEventListener
                    }//fin del runnable
                },1100);//fin del Handler().postDelayed
            }//fin del if user != null
        }//fin del if getCurrentUser

        else { //Si el usuario no esta logeado accedemos al login de usuario
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1100);
        }

    }
}