package grax_o_no_team.graxono;

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

public class History extends AppCompatActivity {
    Cursor cursor;
    Adapter adapter;
    ListView listView;
    DbManager manager;
    TextView text;
    Button search, menu;
    EditText searchText;
    int user;

    static String[] from = new String[]{DbManager.CN_TITLE,DbManager.CN_USER_POSITIVES,DbManager.CN_VIDEO_POSITIVES,DbManager.CN_USER_TOTAL,DbManager.CN_VIDEO_TOTAL, DbManager.CN_ASSET,DbManager.CN_URL};
    static int[] to = new int[]{ R.id.titleElement, R.id.userPositives, R.id.totalPositives,R.id.totalUser,R.id.totalVideo,R.id.imageElement};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        listView=(ListView) findViewById(R.id.lista);
        text=(TextView) findViewById(R.id.debugElement);
        search =(Button) findViewById(R.id.buttonSEARCH);
        menu=(Button) findViewById(R.id.buttonMenu);
        searchText=(EditText) findViewById(R.id.searchText);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferences", Context.MODE_PRIVATE);
        user = sharedPref.getInt("User",1);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdaptor();
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
                Intent intent = new Intent(getApplicationContext(), Answer.class );
                intent.putExtra("title",cursor.getString(1));
                intent.putExtra("video",cursor.getString(7));
                intent.putExtra("total",cursor.getInt(5));
                intent.putExtra("positives",cursor.getInt(3));
                intent.putExtra("Answer",true);
                startActivity(intent);
            }

        });

        manager = new DbManager(this);
        updateAdaptor();
    }

    public void updateAdaptor(){
        String textDeBusqueda = searchText.getText().toString();
        if (textDeBusqueda.matches(""))
            cursor = manager.searchUserVideos(user);
        else
            cursor = manager.searchUserVideosTitle(user,textDeBusqueda);
        if(!cursor.moveToFirst()){
            text.setText(getString(R.string.empty));
        }
        adapter = new SimpleCursorAdapter(this, R.layout.list_element, cursor, from, to, 0);
        listView.setAdapter((ListAdapter) adapter);
    }
}
