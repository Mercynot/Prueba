package com.example.prueba;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        EditText etNombre = findViewById(R.id.etNombre);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnMapa = findViewById(R.id.btnMapa);
        Button btnLlamar = findViewById(R.id.btnLlamar);
        Button btnAyuda = findViewById(R.id.btnAyuda);

        // intent explicito que se enviara a MainActivity
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nombre_editado", nombre);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // intent implicito para abrir ubicación en gogle Maps
        btnMapa.setOnClickListener(v -> {

            Uri gmmIntentUri = Uri.parse("geo:-33.5284,-70.6627?q=Santo Tomas");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });


        btnLlamar.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:6004444444"));
            startActivity(callIntent);
        });


        btnAyuda.setOnClickListener(v -> {
            startActivity(new Intent(PerfilActivity.this, AyudaActivity.class));
        });
    }
}