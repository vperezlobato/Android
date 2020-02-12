package es.iesnervion.victor.sharedprefejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    TextView mostrarUsuario;
    TextView mostrarContrasena;
    Button mostrar,ant;
    public static final String mypreference = "mypref";
    public static final String Usuario = "usuario";
    public static final String Contrasena = "contrasena";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mostrarUsuario = findViewById(R.id.mostrarUsuario);
        mostrarContrasena = findViewById(R.id.mostrarContrasena);
        mostrar = findViewById(R.id.mostrarDatos);
        ant = findViewById(R.id.ant);

        mostrar.setOnClickListener(this);
        ant.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mostrarDatos:
                SharedPreferences pref = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                if(pref.contains(Usuario)){
                    mostrarUsuario.setText(pref.getString(Usuario,""));
                }
                if(pref.contains(Contrasena)){
                    mostrarContrasena.setText(pref.getString(Contrasena,""));
                }

                break;

            case R.id.ant:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            break;

        }

    }
}
