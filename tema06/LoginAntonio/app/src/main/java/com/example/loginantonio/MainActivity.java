package com.example.loginantonio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    private static final String CORREO_VALIDO = "antonio@correo.com";
    private static final String CLAVE_VALIDA = "1234";

    // Añadido: Atributo global para el contador
    private int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etCorreo = findViewById(R.id.cajaTextoCorreo);
        EditText etClave = findViewById(R.id.cajaTextoPass);
        MaterialSwitch swRecordar = findViewById(R.id.swRecordar);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);

        btnContinuar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String clave = etClave.getText().toString();


            if (correo.isEmpty()) {
                etCorreo.setError(getString(R.string.falta_correo));
                return;
            }
            if (clave.isEmpty()) {
                etClave.setError(getString(R.string.falta_clave));
                return;
            }

            if (correo.equals(CORREO_VALIDO) && clave.equals(CLAVE_VALIDA)) {
                intentos = 0;
                tvMensaje.setTextColor(ContextCompat.getColor(this, R.color.correcto));
                if (swRecordar.isChecked()) {
                    tvMensaje.setText(R.string.acceso_recordado);
                } else {
                    tvMensaje.setText(R.string.acceso_correcto);
                }
            } else {
                intentos++;
                tvMensaje.setTextColor(ContextCompat.getColor(this, R.color.incorrecto));

                if (intentos >= 3) {
                    tvMensaje.setText(R.string.demasiados_intentos);
                    btnContinuar.setEnabled(false);
                    Toast.makeText(this, R.string.demasiados_intentos, Toast.LENGTH_SHORT).show();
                } else {
                    tvMensaje.setText(R.string.acceso_incorrecto);
                }
            }
        });
    }
}