package com.pauljordan.cactuapp.GestorComunitarioFolder;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.models.AmenazaModel;
import com.pauljordan.cactuapp.models.ReporteEventoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReporteEventoActivity extends AppCompatActivity {

    private TextView nombreTextView, rolTextView, fechaTextView,
                     correoTextView, comunidadTextView, parroquiaTextView,
                     telefonoTextView, tipoEventoTextView;
    private Button btncargarEvento;

    private DatabaseReference mDatabase;
    private AmenazaModel tipoAmenaza;
    private FirebaseStorage mStorage;
    private ImageButton imageButtonEvento;

    private ProgressDialog progressDialog;
    private TextInputEditText inputEditTextDescripcion;

    private static final int Gallery_Code = 1;
    Uri imageUrl = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_evento);
        Date date = new Date();

        //cast TextView
         nombreTextView = findViewById(R.id.reporteNombre);
         rolTextView = findViewById(R.id.reporteRol);
         fechaTextView = findViewById(R.id.reporteFecha);
         correoTextView = findViewById(R.id.reporteCorreo);
         parroquiaTextView = findViewById(R.id.reporteParroquia);
         comunidadTextView = findViewById(R.id.reporteComunidad);
         telefonoTextView = findViewById(R.id.reporteTelefono);
         tipoEventoTextView = findViewById(R.id.reporteEvento);
         //cast imageButton
         imageButtonEvento = findViewById(R.id.imageButtonEvento);
         //cast TextInputEditText
         inputEditTextDescripcion = findViewById(R.id.reporteDescripcion);
         //Button
        btncargarEvento = findViewById(R.id.btnEnviarReporte);

         mDatabase = FirebaseDatabase.getInstance().getReference();
         mStorage = FirebaseStorage.getInstance();
         progressDialog = new ProgressDialog(this);

        //Utilizamos Una instancia del FirebaseUser para obtener el usuario que esta logeado actualmente
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Obtenemos el tipo de Evento
        Bundle bundle = this.getIntent().getExtras();
        tipoAmenaza = (AmenazaModel) bundle.getSerializable("tipoEvento");

        //Consultamos datos del usuario registrado que iran adjuntos en el reporte
         mDatabase.child("Usuario").child(user.getUid()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()){
                     //validamos que existan los nodos
                     String nombre = snapshot.child("nombre").getValue().toString().trim();
                     String correo = snapshot.child("correo").getValue().toString().trim();
                     String rol = snapshot.child("rol").getValue().toString().trim();
                     String parroquia = snapshot.child("parroquia").getValue().toString().trim();
                     String comunidad = snapshot.child("comunidad").getValue().toString().trim();
                     String telefono = snapshot.child("telefono").getValue().toString().trim();

                     //pasamos los objetos nombre y rol a las vistas
                     nombreTextView.setText(nombre);
                     correoTextView.setText(correo);
                     rolTextView.setText(rol);
                     //consultamos la fecha actual del dispositivo con Simple DAta Format
                     SimpleDateFormat fecha = new SimpleDateFormat("d,MMMM,yyyy");
                     String currentFecha = fecha.format(date);
                     //pasamos el currentFecha a la vista
                     fechaTextView.setText(currentFecha);
                     parroquiaTextView.setText(parroquia);
                     comunidadTextView.setText(comunidad);
                     telefonoTextView.setText(telefono);
                     tipoEventoTextView.setText(tipoAmenaza.getAmenaza());
                 }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });//fin del addValueEventListener


        imageButtonEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });//fin del imageButtonEvento.setOnClickListener

    }//fin del onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_Code && resultCode==RESULT_OK){
            imageUrl = data.getData();
            imageButtonEvento.setImageURI(imageUrl);
        }
        //Boton que sube el registro del evento a BD
        btncargarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreR = nombreTextView.getText().toString();
                String correoR = correoTextView.getText().toString();
                String rolR = rolTextView.getText().toString();
                String fecha = fechaTextView.getText().toString();
                String parroquiaR = parroquiaTextView.getText().toString();
                String comunidadR = comunidadTextView.getText().toString();
                String telefonoR = telefonoTextView.getText().toString();
                String eventoR = tipoEventoTextView.getText().toString();
                String descripcionR = inputEditTextDescripcion.getText().toString();

                if (!(descripcionR.isEmpty()&&nombreR.isEmpty()&&correoR.isEmpty()
                        &&rolR.isEmpty()&&fecha.isEmpty()&&parroquiaR.isEmpty()
                        &&comunidadR.isEmpty()&&telefonoR.isEmpty()&&eventoR.isEmpty())){
                    progressDialog.setTitle("Enviando...");
                    progressDialog.setMessage("Espere, por favor");
                    progressDialog.show();

                    StorageReference filePath = mStorage.getReference().child("imagenEvento").child(imageUrl.getLastPathSegment());
                    filePath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlDescarga = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    ReporteEventoModel r = new ReporteEventoModel();
                                    r.setUid(UUID.randomUUID().toString());
                                    r.setNombre(nombreR);
                                    r.setCorreo(correoR);
                                    r.setRol(rolR);
                                    r.setFecha(fecha);
                                    r.setParroquia(parroquiaR);
                                    r.setComunidad(comunidadR);
                                    r.setTelefono(telefonoR);
                                    r.setEvento(eventoR);
                                    r.setDescripcion(descripcionR);
                                    r.setImagen(t);
                                    mDatabase.child("Reporte_Evento").child(r.getUid()).setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(ReporteEventoActivity.this, "Evento Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(ReporteEventoActivity.this,MainGestorComunitarioActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }//fin del onComplete2
                                    });//fin del addOnCompleteListener2
                                }//fin del onComplete1
                            });//fin del addOnCompleteListener1
                        }//fin del onSuccess
                    });//fin del addOnSuccessListener
                }// fin del if Validacion campos vacios
            }//fin del onClick
        });//fin del btncargarEvento.setOnClickListener
    }//fin del onActivityResult

}//fin de la clase