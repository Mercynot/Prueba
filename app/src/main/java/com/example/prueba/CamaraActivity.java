package com.example.prueba;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CamaraActivity extends AppCompatActivity {

    private ImageView imagenPrevia;
    private Uri urlImagen;

    // parametros permisos
    private final ActivityResultLauncher<String> permisoCamaraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), carga -> {
                if (carga) tomarFoto();
                else Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            });

    // parametro para tomar la foto y recibir el resultado
    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), okay -> {
                if (okay && urlImagen != null) {
                    imagenPrevia.setImageURI(urlImagen);
                    Toast.makeText(this, "Foto guardada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Captura cancelada", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        Button btnTomarFoto = findViewById(R.id.btnTomarFoto);
        imagenPrevia = findViewById(R.id.imgPreview);

        btnTomarFoto.setOnClickListener(v -> checkPermisoYTomar());
    }



    private void checkPermisoYTomar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            tomarFoto();
        } else {
            permisoCamaraLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void tomarFoto() {
        File fotoArchivo = null;
        try {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File storageDir = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
            fotoArchivo = File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
        } catch (IOException ex) {
            Toast.makeText(this, "Error al crear archivo", Toast.LENGTH_SHORT).show();
        }

        if (fotoArchivo != null) {

            urlImagen = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fotoArchivo);
            takePictureLauncher.launch(urlImagen);
        }
    }
}