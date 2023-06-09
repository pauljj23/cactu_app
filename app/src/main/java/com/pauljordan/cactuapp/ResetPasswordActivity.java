package com.pauljordan.cactuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText resetCorreo;
    private MaterialButton resetButton;
    private String correo="";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //progress dialog mensaje que se muestra al presional el boton reset
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDialog = new ProgressDialog(this);

        resetCorreo = findViewById(R.id.resetCorreoTextField);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = resetCorreo.getText().toString();
                if (!correo.isEmpty()){
                    mDialog.setMessage("Enviando correo...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    //llamada al metodo resetPassword
                    resetPassword();

                }else {
                    Toast.makeText(ResetPasswordActivity.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();
                }
            }//fin del onClick

        });//fin del setOnClickListener

    }//fin del onCreate

    private void resetPassword(){
        //especificamos el idioma del mensaje de correo, en este caso español ES
        mAuth.setLanguageCode("es");
        //enviamos el correo elesctronico con el link de reseteo de contraseña
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Se ha enviado un correo para reestablecer su contraseña ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    finish();

                }else{
                    Toast.makeText(ResetPasswordActivity.this, "No se pudo enviar el correo para restablecer contraseña", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
}//fin ResetPasswordActivity