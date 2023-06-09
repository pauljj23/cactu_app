package com.pauljordan.cactuapp.AdministradorFolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pauljordan.cactuapp.LoginActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.models.AmenazaModel;

import java.util.UUID;

public class RegisterEventoActivity extends AppCompatActivity {
    private TextInputEditText editTextEvento;
    private Button btnRegistrarEvento;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_evento);

        editTextEvento = findViewById(R.id.registerAdminAmenaza);
        btnRegistrarEvento = findViewById(R.id.btnRegistrarAmenaza);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Amenazas");

        btnRegistrarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amenaza =  editTextEvento.getText().toString().trim();
                if (!amenaza.isEmpty()){
                    AmenazaModel amenazaModel = new AmenazaModel();
                    amenazaModel.setAmenaza(amenaza);
                    amenazaModel.setUid(UUID.randomUUID().toString());
                    mRef.child(amenazaModel.getUid()).setValue(amenazaModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterEventoActivity.this, "Amenaza registrada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterEventoActivity.this, AdministrarEventosActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterEventoActivity.this, "Debe ingresar una amenaza", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }// fin del oncreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterEventoActivity.this, AdministrarEventosActivity.class);
        startActivity(intent);
        finish();
    }
}//fin de la clase