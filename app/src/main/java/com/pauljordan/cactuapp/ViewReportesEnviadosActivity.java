package com.pauljordan.cactuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.UrlUriLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pauljordan.cactuapp.GestorComunitarioFolder.ReportesEnviadosActivity;
import com.pauljordan.cactuapp.R;
import com.pauljordan.cactuapp.models.AmenazaModel;
import com.pauljordan.cactuapp.models.ReporteEventoModel;

public class ViewReportesEnviadosActivity extends AppCompatActivity {
    private TextView viewNombre, viewCorreo, viewRol, viewFecha, viewParroquia, viewComunidad, viewTelefono, viewEvento, viewDescripcion;
    private ImageView viewImageEvento;
    private DatabaseReference mDatabase;
    ReporteEventoModel reporteEnviado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reportes_enviados);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //obtenemos el putEXtra enviado desde el adapter
        Bundle bundle = this.getIntent().getExtras();
        reporteEnviado = (ReporteEventoModel) bundle.getSerializable("viewReportesEnviados");

        //instancia de la vista
        viewNombre = findViewById(R.id.viewreporteNombre);
        viewCorreo = findViewById(R.id.viewreporteCorreo);
        viewRol = findViewById(R.id.viewreporteRol);
        viewFecha = findViewById(R.id.viewreporteFecha);
        viewParroquia = findViewById(R.id.viewreporteParroquia);
        viewComunidad = findViewById(R.id.viewreporteComunidad);
        viewTelefono = findViewById(R.id.viewreporteTelefono);
        viewEvento = findViewById(R.id.viewreporteEvento);
        viewDescripcion = findViewById(R.id.viewreporteDescripcion);
        viewImageEvento = findViewById(R.id.viewimageEvento);


        viewNombre.setText(reporteEnviado.getNombre());
        viewCorreo.setText(reporteEnviado.getCorreo());
        viewRol.setText(reporteEnviado.getRol());
        viewFecha.setText(reporteEnviado.getFecha());
        viewParroquia.setText(reporteEnviado.getParroquia());
        viewComunidad.setText(reporteEnviado.getComunidad());
        viewTelefono.setText(reporteEnviado.getTelefono());
        viewEvento.setText(reporteEnviado.getEvento());
        viewDescripcion.setText(reporteEnviado.getDescripcion());

        Glide.with(ViewReportesEnviadosActivity.this).load(reporteEnviado.getImagen()).into(viewImageEvento);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent = new Intent(ViewReportesEnviadosActivity.this, ReportesEnviadosActivity.class);
        startActivity(intent);*/
        finish();
    }
}