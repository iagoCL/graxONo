package grax_o_no_team.graxono;

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



public class Play extends AppCompatActivity {

    private Cursor cursor;
    TextView text;
    Button buttonFun, buttonNoFun;
    DbManager db;
    VideoView videoView;

    boolean paused;
    int posPause;

    String title, URL;
    int total, positives, videoID;

    int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        text=(TextView) findViewById(R.id.titleVideo);
        buttonFun=(Button) findViewById(R.id.isFunny);
        buttonNoFun=(Button) findViewById(R.id.notFunny);
        videoView = (VideoView) findViewById(R.id.videoView);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferences", Context.MODE_PRIVATE);
        user = sharedPref.getInt("User",1);


        buttonFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(true);
            }

        });
        buttonNoFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(false);
            }

        });

        db = new DbManager(this);
        cursor = db.randomVideo();
        if (!cursor.moveToFirst()){
            text.setText(getString(R.string.empty));
        }else {
            videoID=cursor.getInt(0);
            title=cursor.getString(1);
            URL=cursor.getString(2);
            total=cursor.getInt(3);
            positives=cursor.getInt(4);
            text.setText(title);
            videoView.setVideoPath(URL);
            videoView.setMediaController(null);
            paused=false;
            videoView.start();
        }
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVideo();
            }
        });
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

    public void launch(boolean gusto){

        if(gusto)
            positives++;
        total++;
        db.addAnswer(videoID,user,gusto);
        Intent intent = new Intent(this, Answer.class );
        intent.putExtra("title",title);
        intent.putExtra("video",URL);
        intent.putExtra("total",total);
        intent.putExtra("positives",positives);
        intent.putExtra("Answer",gusto);
        startActivity(intent);
    }
}
