package grax_o_no_team.graxono;

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

public class Credits extends AppCompatActivity {

    Button buttonMenu;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        buttonMenu=(Button) findViewById(R.id.menu);
        text=(TextView) findViewById(R.id.creditsText);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml(getString(R.string.credits),Html.FROM_HTML_MODE_LEGACY));
        } else {
            text.setText(Html.fromHtml(getString(R.string.credits)));
        }
        text.setMovementMethod(new ScrollingMovementMethod());
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });
    }
}

