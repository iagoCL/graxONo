package iago_alvaro.graxono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class usuarios extends AppCompatActivity {

    Button botonMenu, botonRegistrar, botonAcceder;
    TextView texto;
    EditText textoNombre, textoContra;
    DbManager db;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarios);

        botonMenu=(Button) findViewById(R.id.menu);
        botonRegistrar=(Button) findViewById(R.id.botonRegistrar);
        botonAcceder=(Button) findViewById(R.id.botonAcceder);
        texto=(TextView) findViewById(R.id.textAccesoUsuario);
        textoNombre=(EditText) findViewById(R.id.textoNombUse);
        textoContra=(EditText) findViewById(R.id.textoContrase);
        db=new DbManager(this);

        botonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accederUsuario();
            }
        });

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferencias", Context.MODE_PRIVATE);

        editor = sharedPref.edit();



    }

    protected void accederUsuario( ) {
        int usuario = db.accederUsuario(textoNombre.getText().toString(),textoContra.getText().toString());
        if(usuario==-1)
            texto.setText(getString(R.string.ERROR_Contrasena_Incorrecta));
        else if (usuario==-2)
            texto.setText(getString(R.string.ERROR_Nombre));
        else {
            editor.putInt("Usuario", usuario);
            editor.commit();
            texto.setText(getString(R.string.Acceso_Correcto) + textoNombre.getText().toString());
        }
    }

    protected void registrarUsuario( ) {
        if(!(textoContra.getText().toString().matches("") || textoNombre.getText().toString().matches(""))) {
            int usuario = db.insertarUsuario(textoNombre.getText().toString(), textoContra.getText().toString());
            if (usuario < 0)
                texto.setText(getString(R.string.ERROR_Usuario_existente));
            else {
                texto.setText(getString(R.string.Acceso_Correcto) + textoNombre.getText().toString());
                editor.putInt("Usuario", usuario);
                editor.commit();
            }
        }
        else
            texto.setText(getString(R.string.ERROR_Contrasena_y_usuario_Necesarias));
    }
}

