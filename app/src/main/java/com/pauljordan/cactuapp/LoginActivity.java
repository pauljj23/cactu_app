package com.pauljordan.cactuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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

public class LoginActivity extends AppCompatActivity {
    private TextView gotoRegistro, gotoOlvideContrasena;
    private TextInputEditText loginEmail, loginPassword;
    private MaterialButton loginButton;

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TextInputEditText
        loginEmail=findViewById(R.id.loginCorreoTextField);
        loginPassword = findViewById(R.id.loginContrasenaTextField);
        //Material Button
        loginButton = findViewById(R.id.iniciarSesionButton);
        //TextView
        gotoOlvideContrasena = findViewById(R.id.olvideContrasenaLabel);
        gotoRegistro = findViewById(R.id.gotoRegistroLabel);
        //FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //cuando el usuario presiona el boton de Iniciar Sesion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = loginEmail.getText().toString();
                password = loginPassword.getText().toString();

                if (!email.isEmpty()&&!password.isEmpty()){
                    //llamada al metodo loginUsuario
                    loginUsuario();
                }else{
                    Toast.makeText(LoginActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });//fin del setOnClickListener loginButton

        //Cuando el usuario presiona la etiqueta Olvide mi contraseña
        gotoOlvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        //cuando el UsuarioModel presiona la etiqueta Eres nuevo, Registrate!
        gotoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,AuthOptionsActivity.class);
                startActivity(intent);
                finish();

            }
        });//fin del setOnClickListener


    }//fin del onCreate

    //Metodo para Iniciar Sesion
    private void loginUsuario(){
        //Iniciamos Sesion con el email y password para luego obtener el UID
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        mDatabase.child("Usuario").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String rol = snapshot.child("rol").getValue().toString();
                                    Toast.makeText(LoginActivity.this, rol, Toast.LENGTH_SHORT).show();
                                    if (rol.equals("Administrador")){

                                        Intent intent = new Intent(LoginActivity.this, MainAdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } //fin del if equals Administrador
                                    else if (rol.equals("Gestor Comunitario")){

                                        Intent intent = new Intent(LoginActivity.this, MainGestorComunitarioActivity.class);

                                        startActivity(intent);
                                        finish();
                                    } //fin del if equals Gestor Comunitario
                                    else if (rol.equals("Tecnico Especialista")) {

                                        Intent intent = new Intent(LoginActivity.this, MainTecnicoEspecialistaActivity.class);

                                        startActivity(intent);
                                        finish();
                                    }//fin del if equals Tecnico Especialista
                                    /*else{
                                        Toast.makeText(LoginActivity.this, "UsuarioModel no registrado", Toast.LENGTH_SHORT).show();
                                    }*/
                                }else{
                                    Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                                }
                            }//fin del onDataChange
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });//fin del addValueEventListener

                    }//fin del if  !=null

                }//fin del isSuccessful
                else{
                    Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }//fin del onClomplete

        });//fin del signInWithEmailAndPassword

    }//fin del metodo loginUsuario

}//fin del LoginActivity