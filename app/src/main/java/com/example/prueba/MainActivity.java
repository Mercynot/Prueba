package com.example.prueba;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // variables x bienvenida, linterna y camara
    private String emailUsuario = "";
    private TextView tvBienvenida;
    private Button btnLinterna;
    private CameraManager camara;
    private String camaraID = null;
    private boolean luz = false;

    // Datos de perfilAc
    private final ActivityResultLauncher<Intent> editarPerfilLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String nombre = result.getData().getStringExtra("nombre_editado");
                    if (nombre != null) {
                        tvBienvenida.setText("Hola, " + nombre);
                    }
                }
            });

    // pedir permiso de camara :v
    private final ActivityResultLauncher<String> permisoCamaraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    alternarluz(); // si conceden permiso, intentamos prender/apagar[cite: 6]
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //indico cual es su view al main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //elementos visuales
        tvBienvenida = findViewById(R.id.tvBienvenida);
        Button btnIrPerfil = findViewById(R.id.btnIrPerfil);
        Button btnAbrirWeb = findViewById(R.id.btnAbrirWeb);
        Button btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        Button btnCompartir = findViewById(R.id.btnCompartir);
        btnLinterna = findViewById(R.id.btnLinterna);
        Button btnCamara = findViewById(R.id.btnCamara);


        emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario == null) emailUsuario = "";
        tvBienvenida.setText("Bienvenido: " + emailUsuario);


        btnIrPerfil.setOnClickListener(v -> {

            Intent i = new Intent(MainActivity.this, PerfilActivity.class);
            i.putExtra("email_usuario", emailUsuario);
            editarPerfilLauncher.launch(i);
        });

        // intent implícito abrir web con un ACtionVIEW
        btnAbrirWeb.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.santotomas.cl");
            Intent viewWeb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(viewWeb);
        });

        // intent implícito enviar correo con action_sendto
        btnEnviarCorreo.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:")); //para que solo abra aplicacion de correo
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUsuario});
            email.putExtra(Intent.EXTRA_SUBJECT, "Prueba desde la app");
            email.putExtra(Intent.EXTRA_TEXT, "Hola, esto es un intento de correo.");
            startActivity(Intent.createChooser(email, "Enviar correo con:"));
        });

        // intent implícito compartir texto con un Action_send y con el chooser obligo al android a que desplege menu para compartir
        btnCompartir.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Hola buenas tardes :v");
            startActivity(Intent.createChooser(share, "Compartir usando:"));
        });

        // llamo al camaramanager y hace un bucle para escanear sus camaras que tengan flash y camara trasera
        camara = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for (String id : camara.getCameraIdList()) {
                CameraCharacteristics cc = camara.getCameraCharacteristics(id);
                Boolean disponibleFlash = cc.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer lensFacing = cc.get(CameraCharacteristics.LENS_FACING);
                if (Boolean.TRUE.equals(disponibleFlash)
                        && lensFacing != null
                        && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    camaraID = id;
                    break;
                }
            }
        } catch (CameraAccessException e) {
            Toast.makeText(this, "No se puede acceder a la cámara", Toast.LENGTH_SHORT).show();  //try y catch para capturar erroes
        }


        btnLinterna.setOnClickListener(v -> {
            if (camaraID == null) {
                Toast.makeText(this, "Este dispositivo no tiene flash disponible", Toast.LENGTH_SHORT).show();   // si no tiene flash
                return;
            }

            boolean camGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)  // y si tiene checkselmission para ver si tiene permisos para ocuparlos
                    == PackageManager.PERMISSION_GRANTED;
            if (camGranted) {
                alternarluz();
            } else {
                permisoCamaraLauncher.launch(Manifest.permission.CAMERA);   //si no tiene permiso
            }
        });


        btnCamara.setOnClickListener(v ->
                startActivity(new Intent(this, CamaraActivity.class)));

        //simulo cargas en segundo plano uso runOnUiThread para evitar crasheos o congelau
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "¡Módulos cargados exitosamente!", Toast.LENGTH_SHORT).show()
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

     //metodo para invertir valor de luz en ves de false que sea true :V
    private void alternarluz() {
        try {
            luz = !luz;
            camara.setTorchMode(camaraID, luz);
            btnLinterna.setText(luz ? "Apagar Linterna" : "Encender Linterna");
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Error al controlar la linterna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {  //para apagar la luz si se minimiza
        super.onPause();
        if (camaraID != null && luz) {
            try {
                camara.setTorchMode(camaraID, false);
                luz = false;
                if (btnLinterna != null) btnLinterna.setText("Encender Linterna");
            } catch (CameraAccessException ignored) {}
        }
    }
}