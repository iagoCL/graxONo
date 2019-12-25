package grax_o_no_team.graxono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */

public class AddVideo extends AppCompatActivity {

    Button buttonMenu, buttonNewVideo;
    TextView text;
    EditText textTitle, textURL;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_video);

        buttonMenu=(Button) findViewById(R.id.menu);
        buttonNewVideo=(Button) findViewById(R.id.buttonNewVideo);
        text=(TextView) findViewById(R.id.textNewVideo);
        textTitle=(EditText) findViewById(R.id.titleVideoAdd);
        textURL=(EditText) findViewById(R.id.textAddUrl);
        db=new DbManager(this);

        buttonNewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newVideo();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });
    }

    protected void newVideo( ) {
        if(!(textURL.getText().toString().matches("") || textTitle.getText().toString().matches(""))) {
            int video = db.addVideo(textTitle.getText().toString(), textURL.getText().toString(),R.raw.icono500);
            if (video < 0)
                text.setText(getString(R.string.ERROR_Repeated_Video));
            else{
                Intent intent = new Intent(getApplicationContext(), Answer.class );
                intent.putExtra("title",textTitle.getText().toString());
                intent.putExtra("video",textURL.getText().toString());
                intent.putExtra("total",2);
                intent.putExtra("positives",1);
                intent.putExtra("Answer",true);
                startActivity(intent);
            }
        }
        else
            text.setText(getString(R.string.ERROR_Missing_Fields));
    }
}