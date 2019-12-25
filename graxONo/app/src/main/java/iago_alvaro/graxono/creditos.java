package iago_alvaro.graxono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by
 * Calvo Lista, Iago
 * Navas Castillo, Álvaro
 */

public class creditos extends AppCompatActivity {

    Button botonMenu;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);

        botonMenu=(Button) findViewById(R.id.menu);
        texto=(TextView) findViewById(R.id.creditosText);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            texto.setText(Html.fromHtml(getString(R.string.creditos),Html.FROM_HTML_MODE_LEGACY));
        } else {
            texto.setText(Html.fromHtml(getString(R.string.creditos)));
        }


        texto.setMovementMethod(new ScrollingMovementMethod());

        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });
    }
}

