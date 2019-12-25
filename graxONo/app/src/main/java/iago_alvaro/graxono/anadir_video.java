package iago_alvaro.graxono;

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

public class anadir_video  extends AppCompatActivity {

    Button botonMenu, botonNuevoVideo;
    TextView texto;
    EditText textoTitulo, textoURL;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anadir_video);

        botonMenu=(Button) findViewById(R.id.menu);
        botonNuevoVideo=(Button) findViewById(R.id.botonNuevoVideo);
        texto=(TextView) findViewById(R.id.textNuevoVideo);
        textoTitulo=(EditText) findViewById(R.id.tituloVideoAnadir);
        textoURL=(EditText) findViewById(R.id.textoURLAnadir);
        db=new DbManager(this);

        botonNuevoVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoVideo();
            }
        });


        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });



    }


    protected void nuevoVideo( ) {
        if(!(textoURL.getText().toString().matches("") || textoTitulo.getText().toString().matches(""))) {
            int video = db.insertarVideo(textoTitulo.getText().toString(), textoURL.getText().toString(),R.raw.icono500);
            if (video < 0)
                texto.setText(getString(R.string.ERROR_Video_Existente));
            else{
                Intent intent = new Intent(getApplicationContext(), respuesta.class );
                intent.putExtra("titulo",textoTitulo.getText().toString());
                intent.putExtra("video",textoURL.getText().toString());
                intent.putExtra("total",2);
                intent.putExtra("positivos",1);
                intent.putExtra("respuesta",true);
                startActivity(intent);


            }
        }
        else
            texto.setText(getString(R.string.ERROR_Faltan_Campos));
    }
}