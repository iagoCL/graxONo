package grax_o_no_team.graxono;

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

public class Users extends AppCompatActivity {

    Button buttonMenu, buttonRegister, buttonAcces;
    TextView text;
    EditText textName, textPassword;
    DbManager db;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        buttonMenu=(Button) findViewById(R.id.menu);
        buttonRegister=(Button) findViewById(R.id.buttonRegister);
        buttonAcces=(Button) findViewById(R.id.buttonAcces);
        text=(TextView) findViewById(R.id.textUserAccess);
        textName=(EditText) findViewById(R.id.textUserName);
        textPassword=(EditText) findViewById(R.id.textPassword);
        db=new DbManager(this);

        buttonAcces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "Preferences", Context.MODE_PRIVATE);

        editor = sharedPref.edit();



    }

    protected void accessUser( ) {
        int user = db.accessUser(textName.getText().toString(),textPassword.getText().toString());
        if(user==-1)
            text.setText(getString(R.string.ERROR_Incorrect_Password));
        else if (user==-2)
            text.setText(getString(R.string.ERROR_Name));
        else {
            editor.putInt("User", user);
            editor.commit();
            text.setText(getString(R.string.Correct_Acces) + textName.getText().toString());
        }
    }

    protected void registerUser( ) {
        if(!(textPassword.getText().toString().matches("") || textName.getText().toString().matches(""))) {
            int user = db.addUser(textName.getText().toString(), textPassword.getText().toString());
            if (user < 0)
                text.setText(getString(R.string.ERROR_Repeated_User));
            else {
                text.setText(getString(R.string.Correct_Acces) + textName.getText().toString());
                editor.putInt("User", user);
                editor.commit();
            }
        }
        else
            text.setText(getString(R.string.ERROR_Pass_And_User_Require));
    }
}

