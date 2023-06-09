package com.pauljordan.cactuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarEventosActivity;
import com.pauljordan.cactuapp.AdministradorFolder.AdministrarTecnicoEspecilistaActivity;
import com.pauljordan.cactuapp.models.AmenazaModel;
import com.pauljordan.cactuapp.models.UsuarioModel;

public class ViewEventosActivity extends AppCompatActivity {
    private TextInputEditText editTextEventos;
    private AmenazaModel amenazaModel;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eventos);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //obtenemos el putEXtra enviado desde el adapter
        Bundle bundle = this.getIntent().getExtras();
        amenazaModel = (AmenazaModel) bundle.getSerializable("viewEvento");
        //instancia de la vista
        editTextEventos = findViewById(R.id.editViewTipoAmenaza);

        editTextEventos.setText(amenazaModel.getAmenaza());
    }//fin del onCreate
    /*------------------------------------------------------------------------------------------------*/
    //Cuando el usuario presiona el boton atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewEventosActivity.this, AdministrarEventosActivity.class);
        startActivity(intent);
        finish();
    }//fin del onBackPressed

    /*------------------------------------------------------------------------------------------------*/
    //CREACION DEL MENU
    //Inflamos el menu del Administrar Gestor Comunitario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_evento_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }//fin del onCreatOptions

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //CASE1 ----------------------------------------------------------------------------------*/
            case R.id.actualizarEvento:{
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventosActivity.this);
                builder.setMessage("¿Desea guardar los cambios realizados?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        final AmenazaModel  a = new AmenazaModel();
                        a.setUid(amenazaModel.getUid());
                        if (a.getUid() !=null){
                            a.setAmenaza(editTextEventos.getText().toString().trim());
                            mDatabase.child("Amenazas").child(a.getUid()).setValue(a);
                            Toast.makeText(ViewEventosActivity.this, "Amenaza Actualizada", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ViewEventosActivity.this, "el id de la amenaza es null", Toast.LENGTH_SHORT).show();
                        }

                    }//fin del onClick
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            }
            //CASE 2 ----------------------------------------------------------------------------------*/
            case R.id.borrarEvento:{
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventosActivity.this);
                builder.setMessage("¿Desea borrar esta amenaza?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AmenazaModel a = new AmenazaModel();
                        a.setUid(amenazaModel.getUid());
                        mDatabase.child("Amenazas").child(a.getUid()).removeValue();
                        Intent intent = new Intent(ViewEventosActivity.this,AdministrarEventosActivity.class);
                        startActivity(intent);
                        finish();
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
    }//fin del onOptionsItemSelected

}//fin de la clase