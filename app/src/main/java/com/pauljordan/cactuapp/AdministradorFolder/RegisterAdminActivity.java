package com.pauljordan.cactuapp.AdministradorFolder;

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
import com.pauljordan.cactuapp.LoginActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.models.UsuarioModel;

public class RegisterAdminActivity extends AppCompatActivity {
    TextInputEditText nameTextInputText, correoTextInputText,
            passwordTextInputText,confirmPassTextInputText,
            numberTextInputText;
    TextView adminGotoLogin;
    MaterialButton adminRegisterButton;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String nombre, email, password, confirmPassword, numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameTextInputText = findViewById(R.id.registerAdminNameTextField);
        correoTextInputText = findViewById(R.id.registerAdminCorreoTextField);
        passwordTextInputText = findViewById(R.id.registerAdminContrasenaTextField);
        confirmPassTextInputText = findViewById(R.id.registerAdminConfirmarContrasenaTextField);

        numberTextInputText = findViewById(R.id.registerAdminTelefonoTextField);

        adminGotoLogin = findViewById(R.id.adminGotologinLabel);

        adminRegisterButton = findViewById(R.id.adminRegistroAdminButton);

        //Cuando el usuario presiona el Label Iniciar Sesión del RegisterActivity regresa al Login - LoginActivity
        adminGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });//fin del gotoLogin

        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = nameTextInputText.getText().toString();
                email = correoTextInputText.getText().toString();
                password = passwordTextInputText.getText().toString();
                confirmPassword = confirmPassTextInputText.getText().toString();
                numero = numberTextInputText.getText().toString();

                //Validamos que los campos no esten vacios
                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !numero.isEmpty()){
                    //Validamos que la contraseña se mayor de 8 caracteres
                    if (password.length()>=8){
                        //validamos que las contraseñas seas iguales
                        if (confirmPassword.equals(password)){
                            //llamamoa al metodo pora Registrar al usuario
                            registrarUsuario();
                        }//fin del if (validacion de contraseñas iguales)
                        else{
                            Toast.makeText(RegisterAdminActivity.this, "", Toast.LENGTH_SHORT).show();
                        }

                    }//fin del if (validacion contraseña > de 8 caracteres)
                    else{
                        Toast.makeText(RegisterAdminActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }//fin del if (validacion de campos vacios)
                else{
                    Toast.makeText(RegisterAdminActivity.this, "Todos los compos deben estar llenos", Toast.LENGTH_SHORT).show();
                }
            }
        });//fin del adminRegisterButton
    }//fin del onCreate

    //Cuando el usuario presione el boton back, regresa al Login - LoginActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterAdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }//fin del onBackPressed

    //Metodo para Registro de usuario
    private void registrarUsuario(){
        Bundle tipoUsuario = this.getIntent().getExtras();
        String usuario = tipoUsuario.getString("rol");

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                       /* Map<String, Object> map = new HashMap<>();
                        map.put("nombre",nombre);
                        map.put("email",email);
                        //map.put("contraseña",password);
                        map.put("telefono",numero);
                        map.put("rol",usuario);*/
                        UsuarioModel u = new UsuarioModel();
                        u.setUid(mAuth.getCurrentUser().getUid());
                        u.setNombre(nombre);
                        u.setCorreo(email);
                        //u.setParroquia(parroquia);
                        //u.setComunidad(comunidad);
                        u.setTelefono(numero);
                        u.setRol(usuario);

                        //String id = Objects.requireNonNull(mAuth.getCurrentUser().getUid());

                        mDatabase.child("Usuario").child(u.getUid()).setValue(u)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            Toast.makeText(RegisterAdminActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterAdminActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            mAuth.signOut();
                                            finish();
                                        }else {
                                            Toast.makeText(RegisterAdminActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });//fin del mDatabase
                    }//fin del if
                    else{
                        Toast.makeText(RegisterAdminActivity.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });//fin del createUserWithEmailAndPassword Gestor Comunitario
    }//fin del metodo registrarUsuario

}