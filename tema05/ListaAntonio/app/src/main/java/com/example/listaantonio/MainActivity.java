package com.example.listaantonio;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        // 1. Los datos: al menos 8 elementos
        ArrayList<Videojuego> videojuegos = new ArrayList<>();
        videojuegos.add(new Videojuego("Spider-Man 2", "PlayStation 5", R.drawable.spiderman2));
        videojuegos.add(new Videojuego("God of War", "PlayStation 4 / 5", R.drawable.god_of_war));
        videojuegos.add(new Videojuego("Hollow Knight", "Multiplataforma", R.drawable.hollow_knight));
        videojuegos.add(new Videojuego("EA Sports FC 27", "Multiplataforma", R.drawable.fifa_27));
        videojuegos.add(new Videojuego("Spider-Man Miles Morales", "PlayStation 5", R.drawable.spiderman2));
        videojuegos.add(new Videojuego("God of War Ragnarok", "PlayStation 5", R.drawable.god_of_war));
        videojuegos.add(new Videojuego("Silksong", "Multiplataforma", R.drawable.hollow_knight));
        videojuegos.add(new Videojuego("FIFA 26", "Multiplataforma", R.drawable.fifa_27));
        videojuegos.add(new Videojuego("Spider-Man Remastered", "PC / PS5", R.drawable.spiderman2));
        videojuegos.add(new Videojuego("God of War III", "PlayStation 4", R.drawable.god_of_war));
        videojuegos.add(new Videojuego("Hollow Knight: Voidheart", "Multiplataforma", R.drawable.hollow_knight));
        videojuegos.add(new Videojuego("EA Sports FC 26", "Multiplataforma", R.drawable.fifa_27));

        // 2. La lista: cómo se colocan las filas y quién las pinta
        RecyclerView rvVideojuegos = findViewById(R.id.rvVideojuegos);
        rvVideojuegos.setLayoutManager(new LinearLayoutManager(this));
        rvVideojuegos.setAdapter(new AdaptadorVideojuegos(videojuegos));
    }
}