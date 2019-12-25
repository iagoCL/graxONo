package grax_o_no_team.graxono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */


public class MainActivity extends AppCompatActivity {
    Button buttonPlay, buttonHistory, buttonAdd, buttonUser, buttonCredits;
    TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferences", Context.MODE_PRIVATE);

        int user =sharedPref.getInt("User",1);
        int load =sharedPref.getInt("Load",1);

        DbManager db;
        if(load==1) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Load", 2);
            editor.commit();
            //DbHelper.DB_erase(this);
            db = new DbManager(this);
            db.exampleDBFill();
            /*try {
                DbHelper.BD_backup();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }else{
            db = new DbManager(this);
        }
        buttonPlay=(Button) findViewById(R.id.showVideoo);
        buttonAdd=(Button) findViewById(R.id.addQuestion);
        buttonHistory=(Button) findViewById(R.id.history);
        buttonUser=(Button) findViewById(R.id.buttonUser);
        buttonCredits=(Button) findViewById(R.id.buttonCredits);
        userName=(TextView) findViewById(R.id.textUser);
        Cursor cursor = db.searchUserById(user);
        if (!cursor.moveToFirst()){
            userName.setText(getString(R.string.ERROR_User));
        }else {
            userName.setText( cursor.getString(0) );
        }

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Play.class );
                startActivity(intent);
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, History.class );
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddVideo.class );
                startActivity(intent);
            }
        });

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Users.class );
                startActivity(intent);
            }
        });

        buttonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Credits.class );
                startActivity(intent);
            }
        });


    }


}
