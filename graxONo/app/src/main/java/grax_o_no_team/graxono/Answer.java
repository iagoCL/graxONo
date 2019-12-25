package grax_o_no_team.graxono;

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

public class Answer extends AppCompatActivity {
    TextView text,textProgress;
    VideoView videoView;
    ProgressBar progressBar;
    Button buttonNext, buttonMenu;
    int progress;
    boolean paused;
    int posPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        Bundle datos = this.getIntent().getExtras();

        text=(TextView) findViewById(R.id.titleVideoAnswer);
        textProgress=(TextView) findViewById(R.id.textProgress);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        buttonNext=(Button) findViewById(R.id.next);
        buttonMenu=(Button) findViewById(R.id.menu);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueVideo();
            }

        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMenu();
            }

        });

        text.setText(datos.getString("title"));
        progress = 100*datos.getInt("positives")/datos.getInt("total");

        videoView = (VideoView) findViewById(R.id.videoViewAnswer);
        videoView.setVideoPath(datos.getString("video"));
        videoView.setMediaController(null);
        videoView.start();
        if(datos.getBoolean("Answer"))
        {
            textProgress.setText(progress+getString(R.string.liked));
            textProgress.setTextColor(Color.parseColor("#01DF01"));
        }else{
            textProgress.setText((100-progress)+getString(R.string.not_liked));
            textProgress.setTextColor(Color.parseColor("#ff0000"));
        }
        progressBar.setProgress(progress);

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVideo();
            }
        });
    }

    public void continueVideo(){
        Intent intent = new Intent(this, Play.class );
        startActivity(intent);
    }

    public void returnMenu(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    public void pauseVideo(){
        if(paused){
            videoView.seekTo(posPause);
            videoView.start();
            paused=false;
        }else{
            posPause=videoView.getCurrentPosition();
            videoView.pause();
            paused=true;
        }
    }
}