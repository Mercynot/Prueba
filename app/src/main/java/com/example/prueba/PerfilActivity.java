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
        setContentView(R.layout.activity_perfil); //conecto el diseño

        EditText etNombre = findViewById(R.id.etNombre); //conecto variables con los compenentes de mi diseño xml
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnMapa = findViewById(R.id.btnMapa);
        Button btnLlamar = findViewById(R.id.btnLlamar);
        Button btnAyuda = findViewById(R.id.btnAyuda);

        // intent explicito que se enviara a MainActivity
        btnGuardar.setOnClickListener(v -> {  //evento al hacer click
            String nombre = etNombre.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nombre_editado", nombre);  //cambiar nombre
            setResult(RESULT_OK, resultIntent);
            finish(); // vuelvo al main
        });

        // intent implicito para abrir ubicación en gogle Maps :V
        btnMapa.setOnClickListener(v -> {

            Uri gmmIntentUri = Uri.parse("geo:-33.5284,-70.6627?q=Santo Tomas");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps"); //obligo a que abra el gugol map:v
            startActivity(mapIntent); //arranca
        });

        // intent implicito abrir telefono y poder llamar
        btnLlamar.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL); //dial para que marce no llame en ves de call:v
            callIntent.setData(Uri.parse("tel:+56976403850")); //numero
            startActivity(callIntent);
        });


        btnAyuda.setOnClickListener(v -> {
            startActivity(new Intent(PerfilActivity.this, AyudaActivity.class)); // que abra la ventana ayuda :V
        });
    }
}