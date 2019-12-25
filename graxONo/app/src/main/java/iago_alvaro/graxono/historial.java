package iago_alvaro.graxono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */

public class historial  extends AppCompatActivity {
    Cursor cursor;
    Adapter adapter;
    ListView listView;
    DbManager manager;
    TextView texto;
    Button buscar, menu;
    EditText textoBusca;
    int usuario;

    static String[] from = new String[]{DbManager.CN_TITULO,DbManager.CN_USUARIO_POSITIVOS,DbManager.CN_VIDEO_POSITIVOS,DbManager.CN_USUARIO_TOTAL,DbManager.CN_VIDEO_TOTAL, DbManager.CN_ASSET,DbManager.CN_URL};
    static int[] to = new int[]{ R.id.tituloElemento, R.id.positivosUsuario, R.id.positivosTotales,R.id.totalesUsuario,R.id.totalesVideo,R.id.imagenElemento};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);

        listView=(ListView) findViewById(R.id.lista);
        texto=(TextView) findViewById(R.id.debugElemento);
        buscar=(Button) findViewById(R.id.botonBuscar);
        menu=(Button) findViewById(R.id.botonMenu);
        textoBusca=(EditText) findViewById(R.id.textoBusca);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferencias", Context.MODE_PRIVATE);
        usuario = sharedPref.getInt("Usuario",1);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarAdaptador();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), respuesta.class );
                intent.putExtra("titulo",cursor.getString(1));
                intent.putExtra("video",cursor.getString(7));
                intent.putExtra("total",cursor.getInt(5));
                intent.putExtra("positivos",cursor.getInt(3));
                intent.putExtra("respuesta",true);
                startActivity(intent);
            }

        });

        manager = new DbManager(this);
        actualizarAdaptador();
    }

    public void actualizarAdaptador(){
        String textoDeBusqueda = textoBusca.getText().toString();
        if (textoDeBusqueda.matches(""))
            cursor = manager.consultarVideosUsuario(usuario);
        else
            cursor = manager.consultarVideosUsuarioTitulo(usuario,textoDeBusqueda);
        if(!cursor.moveToFirst()){
            texto.setText(getString(R.string.vacio));
        }
        adapter = new SimpleCursorAdapter(this, R.layout.elemento_lista, cursor, from, to, 0);
        listView.setAdapter((ListAdapter) adapter);
    }
}
