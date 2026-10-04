package com.example.prueba;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        Button btnVolver = findViewById(R.id.btnVolver);

        // boton para devolverse
        btnVolver.setOnClickListener(v -> finish());
    }
}