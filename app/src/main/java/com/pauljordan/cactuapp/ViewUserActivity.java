package com.pauljordan.cactuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarGestorComunitarioActivity;
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarTecnicoEspecilistaActivity;
import com.pauljordan.cactuapp.models.UsuarioModel;

public class ViewUserActivity extends AppCompatActivity  {
    //Objetos de la clase
    private TextView  textViewRol;
    private TextInputEditText textInputEditTextName, textInputEditTextParroquia,
            textInputEditTextComunidad, textInputEditTextTelefono, textInputEditTextCorreo;
    private UsuarioModel usuarioDetail;

    //Firebase
    DatabaseReference mDatabase;
    FirebaseUser user;

    /*------------------------------------------------------------------------------------------------*/
    //ON_CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        setTitle(getClass().getSimpleName());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        //obtenemos el tipo de UsuarioModel si es Gestor o Tecnico
        Bundle bundle = this.getIntent().getExtras();
        usuarioDetail = (UsuarioModel) bundle.getSerializable("view");

        textViewRol = findViewById(R.id.viewUserRol);

        textInputEditTextName = findViewById(R.id.editViewUserName);
        textInputEditTextCorreo = findViewById(R.id.editViewUserMail);
        textInputEditTextComunidad = findViewById(R.id.editViewUserComunidad);
        textInputEditTextParroquia = findViewById(R.id.editViewUserParroquia);
        textInputEditTextTelefono = findViewById(R.id.editViewUserTelefono);

        //obtenemos el putEXtra enviado desde el adapter
        textViewRol.setText(usuarioDetail.getRol());

        textInputEditTextName.setText(usuarioDetail.getNombre());
        textInputEditTextCorreo.setText(usuarioDetail.getCorreo());
        textInputEditTextParroquia.setText(usuarioDetail.getParroquia());
        textInputEditTextComunidad.setText(usuarioDetail.getComunidad());
        textInputEditTextTelefono.setText(usuarioDetail.getTelefono());


    }//fin del onCreate
    /*------------------------------------------------------------------------------------------------*/

    //ON_BACK_PRESSED
    //Cuando el usuario presione el boton atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (usuarioDetail.getRol().equals("Tecnico Especialista")){
            Intent intent = new Intent(getApplicationContext(), AdministrarTecnicoEspecilistaActivity.class);
            startActivity(intent);
            finish();
        }else if(usuarioDetail.getRol().equals("Gestor Comunitario")) {
            Intent intent = new Intent(getApplicationContext(), AdministrarGestorComunitarioActivity.class);
            startActivity(intent);
            finish();
        }

    }//fin del onBackPressed

    /*------------------------------------------------------------------------------------------------*/
    //CREACION DEL MENU
    //Inflamos el menu del Administrar Gestor Comunitario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }//fin del onCreatOptions

    //indicamos que accion realiza cada item del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //item del layOut options_menu
        switch (item.getItemId())
        {
            case R.id.guardar:{
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewUserActivity.this);
                builder.setMessage("¿Desea guardar los cambios realizados?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        final UsuarioModel u = new UsuarioModel();
                        u.setUid(usuarioDetail.getUid());
                        if (u.getUid() !=null){
                        u.setNombre(textInputEditTextName.getText().toString().trim());
                        u.setCorreo(textInputEditTextCorreo.getText().toString().trim());
                        u.setParroquia(textInputEditTextParroquia.getText().toString().trim());
                        u.setComunidad(textInputEditTextComunidad.getText().toString().trim());
                        u.setTelefono(textInputEditTextTelefono.getText().toString().trim());
                        u.setRol(usuarioDetail.getRol());
                        mDatabase.child("Usuario").child(u.getUid()).setValue(u);
                            Toast.makeText(ViewUserActivity.this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ViewUserActivity.this, "El id de Usuario es nulo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            }

        }//fin del switch
        return super.onOptionsItemSelected(item);
    }
    /*------------------------------------------------------------------------------------------------*/
}
