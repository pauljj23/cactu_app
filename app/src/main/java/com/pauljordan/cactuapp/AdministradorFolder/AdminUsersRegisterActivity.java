package com.pauljordan.cactuapp.AdministradorFolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.models.UsuarioModel;

public class AdminUsersRegisterActivity extends AppCompatActivity {
    //Objetos
    private TextInputEditText addAdminName, addAdminCorreo,
            addAdminPassword,addAdminConfirmPass,
            addAdminParroquia,addAdminComunidad,addAdminNumber;

    private MaterialButton addAdminRegisterButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth2;
    private FirebaseOptions firebaseOptions;
    private DatabaseReference mDatabase;
    private String nombre, email, password, confirmPassword, parroquia, comunidad, numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_register);

        mAuth = FirebaseAuth.getInstance();

        firebaseOptions = new  FirebaseOptions.Builder()
                .setDatabaseUrl("https://cactuapp-58637-default-rtdb.firebaseio.com/")
                .setApiKey("AIzaSyCNhj390uMFnlIa3yY1DpT-nva-XWzxNY0")
                .setApplicationId("1:412909693591:android:eb3564f04a04e1daff97d2").build();

        try {
            FirebaseApp myApp;
            myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions,"cactu");
            mAuth2=FirebaseAuth.getInstance(myApp);
        }catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("cactu"));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        addAdminName= findViewById(R.id.registerAdminName);
        addAdminCorreo = findViewById(R.id.registerAdminCorreo);
        addAdminPassword = findViewById(R.id.registerAdminContrasena);
        addAdminConfirmPass = findViewById(R.id.registerAdminConfirmarContrasena);
        addAdminParroquia = findViewById(R.id.registerAdminParroquia);
        addAdminComunidad = findViewById(R.id.registerAdminComunidad);
        addAdminNumber = findViewById(R.id.registerAdminTelefono);

        addAdminRegisterButton = findViewById(R.id.registroAdminButton);

        addAdminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = addAdminName.getText().toString();
                email = addAdminCorreo.getText().toString();
                password = addAdminPassword.getText().toString();
                confirmPassword = addAdminConfirmPass.getText().toString();
                parroquia = addAdminParroquia.getText().toString();
                comunidad = addAdminComunidad.getText().toString();
                numero = addAdminNumber.getText().toString();
                //Validamos que los campos no esten vacios
                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() &&
                        !parroquia.isEmpty() && !comunidad.isEmpty() && !numero.isEmpty()){
                    //Validamos que la contraseña se mayor de 8 caracteres
                    if (password.length()>=8){
                        //validamos que las contraseñas seas iguales
                        if (confirmPassword.equals(password)){
                            //llamamoa al metodo pora Registrar al usuario
                            registrarUsuario();
                        }//fin del if (validacion de contraseñas iguales)
                        else {
                            Toast.makeText(AdminUsersRegisterActivity.this, "Las contrsenas no coinciden", Toast.LENGTH_SHORT).show();
                        }

                    }//fin del if (validacion contraseña > de 8 caracteres)
                    else{
                        Toast.makeText(AdminUsersRegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }//fin del if (validacion de campos vacios)
                else{
                    Toast.makeText(AdminUsersRegisterActivity.this, "Todos los compos deben estar llenos", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }//fin del onCreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle rolUsuario = this.getIntent().getExtras();
        String rolUser = rolUsuario.getString("rol");
        if (rolUser.equals("Gestor Comunitario")){
            Intent intent = new Intent(getApplicationContext(),AdministrarGestorComunitarioActivity.class);
            startActivity(intent);
            finish();
        } else if (rolUser.equals("Tecnico Especialista")) {
            Intent intent = new Intent(getApplicationContext(),AdministrarTecnicoEspecilistaActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Metodo para Registro de usuario
    private void registrarUsuario(){
        Bundle rolUsuario = this.getIntent().getExtras();
        String rolUser = rolUsuario.getString("rol");
        Toast.makeText(this, rolUser, Toast.LENGTH_SHORT).show();

        if (rolUser.equals("Gestor Comunitario")){
            mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //String id = task.getResult().getUser().getUid();
                        UsuarioModel u = new UsuarioModel();
                        u.setUid(mAuth2.getUid());
                        u.setNombre(nombre);
                        u.setCorreo(email);
                        u.setParroquia(parroquia);
                        u.setComunidad(comunidad);
                        u.setTelefono(numero);
                        u.setRol(rolUser);
                        mDatabase.child("UsuarioModel").child(u.getUid()).setValue(u)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            Toast.makeText(AdminUsersRegisterActivity.this, "UsuarioModel Registrado", Toast.LENGTH_SHORT).show();
                                            mAuth2.signOut();
                                            Intent intent = new Intent(AdminUsersRegisterActivity.this, AdministrarGestorComunitarioActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(AdminUsersRegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });//fin del mDatabase
                    }//fin del if
                    else{
                        Toast.makeText(AdminUsersRegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fin del createUserWithEmailAndPassword Gestor Comunitario

        }//fin del equals Gestor Comunitario

        //Comparacion: Si usuario es igual a Tecnico Especialista
        else if (rolUser.equals("Tecnico Especialista")){
            mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        UsuarioModel u = new UsuarioModel();
                        u.setUid(mAuth2.getUid());
                        u.setNombre(nombre);
                        u.setCorreo(email);
                        u.setParroquia(parroquia);
                        u.setComunidad(comunidad);
                        u.setTelefono(numero);
                        u.setRol(rolUser);
                        mDatabase.child("UsuarioModel").child(u.getUid()).setValue(u)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            Toast.makeText(AdminUsersRegisterActivity.this, "UsuarioModel Registrado", Toast.LENGTH_SHORT).show();
                                            //mAdminAuth.signOut();
                                            Intent intent = new Intent(AdminUsersRegisterActivity.this, AdministrarTecnicoEspecilistaActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(AdminUsersRegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });//fin del mDatabase
                    }//fin del if
                    else{
                        Toast.makeText(AdminUsersRegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fin del createUserWithEmailAndPassword Gestor Comunitario
        }else{
            Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
        }//fin del equals Tecnico Especialista

    }//fin del metodo registrarUsuario


}//fin del la clase