package iago_alvaro.graxono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */



public class jugar extends AppCompatActivity {

    private Cursor cursor;
    TextView texto;
    Button botonSiGrax, botonNoGrax;
    DbManager db;
    VideoView videoView;

    boolean pausado;
    int posPausa;

    String titulo, URL;
    int total, positivos, videoID;

    int usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jugar);

        texto=(TextView) findViewById(R.id.tituloVideo);
        botonSiGrax=(Button) findViewById(R.id.siGusta);
        botonNoGrax=(Button) findViewById(R.id.noGusta);
        videoView = (VideoView) findViewById(R.id.videoView);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferencias", Context.MODE_PRIVATE);
        usuario = sharedPref.getInt("Usuario",1);


        botonSiGrax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzar(true);
            }

        });
        botonNoGrax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzar(false);
            }

        });

        db = new DbManager(this);
        cursor = db.videoAlAzar();
        if (!cursor.moveToFirst()){
            texto.setText(getString(R.string.vacio));
        }else {
            videoID=cursor.getInt(0);
            titulo=cursor.getString(1);
            URL=cursor.getString(2);
            total=cursor.getInt(3);
            positivos=cursor.getInt(4);
            texto.setText(titulo);
            videoView.setVideoPath(URL);
            videoView.setMediaController(null);
            pausado=false;
            videoView.start();
        }
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausarVideo();
            }
        });
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

    public void lanzar(boolean gusto){

        if(gusto)
            positivos++;
        total++;
        db.responder(videoID,usuario,gusto);
        Intent intent = new Intent(this, respuesta.class );
        intent.putExtra("titulo",titulo);
        intent.putExtra("video",URL);
        intent.putExtra("total",total);
        intent.putExtra("positivos",positivos);
        intent.putExtra("respuesta",gusto);
        startActivity(intent);
    }
}
