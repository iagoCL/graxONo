package iago_alvaro.graxono;

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
    Button botonJugar, botonHisto, botonAnadir, botonUsuario, botonCreditos;
    TextView nombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferencias", Context.MODE_PRIVATE);

        int usuario =sharedPref.getInt("Usuario",1);
        int cargar =sharedPref.getInt("Cargar",1);

        DbManager db;
        if(cargar==1) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Cargar", 2);
            editor.commit();
            //DbHelper.DB_erase(this);
            db = new DbManager(this);
            db.rellenarDB();
            /*try {
                DbHelper.BD_backup();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }else{
            db = new DbManager(this);
        }
        botonJugar=(Button) findViewById(R.id.verVideo);
        botonAnadir=(Button) findViewById(R.id.añadirPregunta);
        botonHisto=(Button) findViewById(R.id.historial);
        botonUsuario=(Button) findViewById(R.id.botonUsuario);
        botonCreditos=(Button) findViewById(R.id.botonCreditos);
        nombreUsuario=(TextView) findViewById(R.id.textUsuario);
        Cursor cursor = db.buscarUsuarioPorId(usuario);
        if (!cursor.moveToFirst()){
            nombreUsuario.setText(getString(R.string.ERROR_Usuario));
        }else {
            nombreUsuario.setText( cursor.getString(0) );
        }

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, jugar.class );
                startActivity(intent);
            }
        });

        botonHisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, historial.class );
                startActivity(intent);
            }
        });

        botonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, anadir_video.class );
                startActivity(intent);
            }
        });

        botonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, usuarios.class );
                startActivity(intent);
            }
        });

        botonCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, creditos.class );
                startActivity(intent);
            }
        });


    }


}
