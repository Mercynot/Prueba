package com.example.prueba;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AyudaActivity extends AppCompatActivity {  //declaro mi clase extend hereda todas las carractericas de una ppantala de un celu

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //metodo principal que hace cuando se abre la pantalla
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda); // conecto mi diseño y lo recrea

        Button btnVolver = findViewById(R.id.btnVolver);

        // boton para devolverse :v
        btnVolver.setOnClickListener(v -> finish());
    }
}