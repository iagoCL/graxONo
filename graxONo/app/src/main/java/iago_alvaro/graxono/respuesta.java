package iago_alvaro.graxono;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */

public class respuesta  extends AppCompatActivity {
    TextView texto,textoProgreso;
    VideoView videoView;
    ProgressBar progresoBara;
    Button botonSig, botonMenu;
    int progreso;
    boolean pausado;
    int posPausa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.respuesta);
        Bundle datos = this.getIntent().getExtras();

        texto=(TextView) findViewById(R.id.tituloVideoRespuesta);
        textoProgreso=(TextView) findViewById(R.id.progresoText);
        progresoBara=(ProgressBar) findViewById(R.id.progressBar);
        botonSig=(Button) findViewById(R.id.siguiente);
        botonMenu=(Button) findViewById(R.id.menu);

        botonSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuar();
            }

        });

        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }

        });

        texto.setText(datos.getString("titulo"));
        progreso = 100*datos.getInt("positivos")/datos.getInt("total");

        videoView = (VideoView) findViewById(R.id.videoViewRespuesta);
        videoView.setVideoPath(datos.getString("video"));
        videoView.setMediaController(null);
        videoView.start();
        if(datos.getBoolean("respuesta"))
        {
            textoProgreso.setText(progreso+getString(R.string.le_gusto));
            textoProgreso.setTextColor(Color.parseColor("#01DF01"));
        }else{
            textoProgreso.setText((100-progreso)+getString(R.string.no_le_gusto));
            textoProgreso.setTextColor(Color.parseColor("#ff0000"));
        }
        progresoBara.setProgress(progreso);

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarVideo();
            }
        });
    }

    public void continuar(){
        Intent intent = new Intent(this, jugar.class );
        startActivity(intent);
    }

    public void regresar(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    public void pausarVideo(){
        if(pausado){
            videoView.seekTo(posPausa);
            videoView.start();
            pausado=false;
        }else{
            posPausa=videoView.getCurrentPosition();
            videoView.pause();
            pausado=true;
        }
    }
}