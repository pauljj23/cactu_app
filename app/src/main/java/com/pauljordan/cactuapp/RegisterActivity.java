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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pauljordan.cactuapp.models.UsuarioModel;

public class RegisterActivity extends AppCompatActivity {
    //Objetos
    private TextInputEditText nameTextInputText, correoTextInputText,
            passwordTextInputText,confirmPassTextInputText,
            parroquiaTextInputText,comunidadTextInputText,numberTextInputText;
    TextView gotoLogin;
    MaterialButton registerButton;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    
    private String nombre, email, password, confirmPassword, parroquia, comunidad, numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameTextInputText = findViewById(R.id.registerNameTextField);
        correoTextInputText = findViewById(R.id.registerCorreoTextField);
        passwordTextInputText = findViewById(R.id.registerContrasenaTextField);
        confirmPassTextInputText = findViewById(R.id.registerConfirmarContrasenaTextField);
        parroquiaTextInputText = findViewById(R.id.registerParroquiaTextField);
        comunidadTextInputText = findViewById(R.id.registerComunidadTextField);
        numberTextInputText = findViewById(R.id.registerTelefonoTextField);

        gotoLogin = findViewById(R.id.gotologinLabel);

        registerButton = findViewById(R.id.registroButton);

        //Cuando el usuario presiona el Label Iniciar Sesión del RegisterActivity regresa al Login - LoginActivity
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });//fin del gotoLogin
        
        //Cuando el usuario presiona el botton Registrarse
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = nameTextInputText.getText().toString();
                email = correoTextInputText.getText().toString();
                password = passwordTextInputText.getText().toString();
                confirmPassword = confirmPassTextInputText.getText().toString();
                parroquia = parroquiaTextInputText.getText().toString();
                comunidad = comunidadTextInputText.getText().toString();
                numero = numberTextInputText.getText().toString();
                
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
                                Toast.makeText(RegisterActivity.this, "Las contrsenas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                            
                        }//fin del if (validacion contraseña > de 8 caracteres)
                        else{
                            Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                }//fin del if (validacion de campos vacios)
                else{
                    Toast.makeText(RegisterActivity.this, "Todos los compos deben estar llenos", Toast.LENGTH_SHORT).show();
                }
            }
        });//fin del registerButton

    }//fin del onCreate

    //Cuando el usuario presione el boton back, regresa al Login - LoginActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }//fin del onBackPressed


    //Metodo para Registro de usuario
    private void registrarUsuario(){
        Bundle tipoUsuario = this.getIntent().getExtras();
        String usuario = tipoUsuario.getString("rol");
        Toast.makeText(this, usuario, Toast.LENGTH_SHORT).show();
        if (usuario.equals("Gestor Comunitario")){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        /*Map<String, Object> map = new HashMap<>();
                        map.put("nombre",nombre);
                        map.put("email",email);
                        //map.put("contraseña",password);
                        map.put("parroquia",parroquia);
                        map.put("comunidad",comunidad);
                        map.put("telefono",numero);
                        map.put("rol",usuario);*/
                        UsuarioModel u = new UsuarioModel();
                        u.setUid(mAuth.getCurrentUser().getUid());
                        u.setNombre(nombre);
                        u.setCorreo(email);
                        u.setParroquia(parroquia);
                        u.setComunidad(comunidad);
                        u.setTelefono(numero);
                        u.setRol(usuario);
                        //String id = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
                        mDatabase.child("Usuario").child(u.getUid()).setValue(u)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            mAuth.signOut();
                                            finish();
                                        }else {
                                            Toast.makeText(RegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });//fin del mDatabase
                    }//fin del if
                    else{
                        Toast.makeText(RegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fin del createUserWithEmailAndPassword Gestor Comunitario
        }//fin del equals Gestor Comunitario

        //Comparacion: Si usuario es igual a Tecnico Especialista
        else if (usuario.equals("Tecnico Especialista")){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        /*Map<String, Object> map = new HashMap<>();
                        map.put("nombre",nombre);
                        map.put("email",email);
                        //map.put("contraseña",password);
                        map.put("parroquia",parroquia);
                        map.put("comunidad",comunidad);
                        map.put("telefono",numero);
                        map.put("rol",usuario);*/
                        UsuarioModel u = new UsuarioModel();
                        u.setUid(mAuth.getCurrentUser().getUid());
                        u.setNombre(nombre);
                        u.setCorreo(email);
                        u.setParroquia(parroquia);
                        u.setComunidad(comunidad);
                        u.setTelefono(numero);
                        u.setRol(usuario);
                        //String id = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
                        mDatabase.child("Usuario").child(u.getUid()).setValue(u)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            mAuth.signOut();
                                            finish();
                                        }else {
                                            Toast.makeText(RegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });//fin del mDatabase
                    }//fin del if
                    else{
                        Toast.makeText(RegisterActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fin del createUserWithEmailAndPassword Gestor Comunitario
        }else{
            Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
        }//fin del equals Tecnico Especialista

    }//fin del metodo registrarUsuario

}//fin del Register Activity