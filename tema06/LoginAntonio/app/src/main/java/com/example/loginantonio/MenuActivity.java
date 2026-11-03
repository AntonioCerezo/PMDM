package com.example.loginantonio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class MenuActivity extends AppCompatActivity {
    public static final String EXTRA_CORREO = "correo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String correo = getIntent().getStringExtra(EXTRA_CORREO);
        TextView tvSaludo = findViewById(R.id.tvSaludo);
        tvSaludo.setText("Hola, " + correo);
    }
}