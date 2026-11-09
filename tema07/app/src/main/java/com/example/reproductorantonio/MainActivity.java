package com.example.reproductorantonio;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private Button btnPlay;
    private Button btnPausa;
    private SeekBar seekBar;
    private TextView tvActual;
    private TextView tvTotal;

    private SoundPool soundPool;
    private Button btnRisa;
    private Button btnAplauso;
    private Button btnDinosaurio;
    private Button btnPunch;
    private int idRisa;
    private int idAplauso;
    private int idDinosaurio;
    private int idPunch;
    private int sonidosCargados = 0;

    private boolean arrastrando = false;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable actualizar = new Runnable() {
        @Override
        public void run() {
            if (player != null) {
                if (!arrastrando) {
                    long posicion = player.getCurrentPosition();
                    seekBar.setProgress((int) posicion);
                    tvActual.setText(formatear(posicion));
                }
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPausa = findViewById(R.id.btnPausa);
        seekBar = findViewById(R.id.seekBar);
        tvActual = findViewById(R.id.tvActual);
        tvTotal = findViewById(R.id.tvTotal);

        btnRisa = findViewById(R.id.btnRisa);
        btnAplauso = findViewById(R.id.btnAplauso);
        btnDinosaurio = findViewById(R.id.btnDinosaurio);
        btnPunch = findViewById(R.id.btnPunch);

        player = new ExoPlayer.Builder(this).build();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.horror_music);
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int estado) {
                if (estado == Player.STATE_READY) {
                    long duracion = player.getDuration();
                    seekBar.setMax((int) duracion);
                    tvTotal.setText(formatear(duracion));
                }
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (player.getPlaybackState() == Player.STATE_ENDED) {
                player.seekTo(0);
            }
            player.play();
        });

        btnPausa.setOnClickListener(v -> player.pause());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar barra) {
                arrastrando = true;
            }

            @Override
            public void onProgressChanged(SeekBar barra, int progreso, boolean delUsuario) {
                if (delUsuario) {
                    tvActual.setText(formatear(progreso));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar barra) {
                player.seekTo(barra.getProgress());
                arrastrando = false;
            }
        });

        AudioAttributes atributos = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(atributos)
                .build();

        idRisa = soundPool.load(this, R.raw.risas, 1);
        idAplauso = soundPool.load(this, R.raw.aplausos, 1);
        idDinosaurio = soundPool.load(this, R.raw.sonido_dinosaurio, 1);
        idPunch = soundPool.load(this, R.raw.punch, 1);

        soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
            if (status == 0) {
                sonidosCargados++;
                if (sonidosCargados == 4) {
                    btnRisa.setEnabled(true);
                    btnAplauso.setEnabled(true);
                    btnDinosaurio.setEnabled(true);
                    btnPunch.setEnabled(true);
                }
            }
        });

        btnRisa.setOnClickListener(v -> soundPool.play(idRisa, 1f, 1f, 1, 0, 1f));
        btnAplauso.setOnClickListener(v -> soundPool.play(idAplauso, 1f, 1f, 1, 0, 1f));
        btnDinosaurio.setOnClickListener(v -> soundPool.play(idDinosaurio, 1f, 1f, 1, 0, 1f));
        btnPunch.setOnClickListener(v -> soundPool.play(idPunch, 1f, 1f, 1, 0, 1f));
    }

    private String formatear(long ms) {
        long segundos = ms / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d", segundos / 60, segundos % 60);
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(actualizar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.pause();
        handler.removeCallbacks(actualizar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}