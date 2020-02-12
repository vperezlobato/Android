package es.iesnervion.victor.sharedprefejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{

    EditText usuario;
    EditText contrasena;
    TextView txtusuario;
    TextView txtcontrasena;
    Button guardar,mostrar,limpiar,sig;

    SharedPreferences pref;
    public static final String mypreference = "mypref";
    public static final String Usuario = "usuario";
    public static final String Contrasena = "contrasena";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        txtusuario = findViewById(R.id.txtUsuario);
        txtcontrasena = findViewById(R.id.txtContrasena);
        guardar = findViewById(R.id.btnGuardar);
        mostrar = findViewById(R.id.btnMostrar);
        limpiar = findViewById(R.id.btnLimpiar);
        sig = findViewById(R.id.sig);
        guardar.setOnClickListener(this);
        mostrar.setOnClickListener(this);
        limpiar.setOnClickListener(this);
        sig.setOnClickListener(this);

        pref = getApplicationContext().getSharedPreferences(mypreference,Context.MODE_PRIVATE);

        if (pref.contains(Usuario)) {
            usuario.setText(pref.getString(Usuario, ""));
        }
        if (pref.contains(Contrasena)) {
            contrasena.setText(pref.getString(Contrasena, ""));

        }
    }
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = pref.edit();
            switch (v.getId()){
                case R.id.btnGuardar:
                    editor.putString(Usuario,usuario.getText().toString());
                    editor.putString(Contrasena,contrasena.getText().toString());
                    editor.commit();
                break;
                case R.id.btnMostrar:
                    if(pref.contains(Usuario)){
                        txtusuario.setText(pref.getString(Usuario,""));
                    }
                    if(pref.contains(Contrasena)){
                    txtcontrasena.setText(pref.getString(Contrasena,""));
                    }
                break;
                case R.id.btnLimpiar:
                    editor.remove(Usuario);
                    editor.remove(Contrasena);
                    editor.commit();
                    editor.clear();
                    editor.commit();
                    txtcontrasena.setText("");
                    txtusuario.setText("");

                break;

                case R.id.sig:
                    Intent intent = new Intent(this,MainActivity2.class);
                    startActivity(intent);
                break;

            }
    }

}
