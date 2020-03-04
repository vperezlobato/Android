package com.example.buscaminas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buscaminas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnRegister;
    private EditText editEmail, editContrasena;
    private TextView accOlvidada;
    private String email, contrasena;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        editEmail = findViewById(R.id.editEmail);
        editContrasena = findViewById(R.id.editContrasena);
        accOlvidada = findViewById(R.id.textPasswordOlvidada);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        accOlvidada.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if(user != null){ //Si ya iniciaste sesion y no la cerraste te envia directamente a la actividad principal
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
            email = editEmail.getText().toString();
            contrasena = editContrasena.getText().toString();

            if (!email.isEmpty() && !contrasena.isEmpty()) {
                iniciarSesion();
            } else
                Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            break;
            case R.id.btnRegister:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
            break;
            //Metodo onclick en el label para que te envie un mensaje al correo en el que puedes cambiar la contraseña
            case R.id.textPasswordOlvidada:
                if (editEmail.getText().toString().isEmpty()) { //Si no tienes un correo electronico introducido cuando pinchas en el label
                    Toast.makeText(getApplication(), "Introduce tu correo electronico registrado", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(editEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) { //Si ha conseguido enviar el correo correctamente
                                Toast.makeText(LoginActivity.this, "Se te han enviado instrucciones para resetear tu contraseña", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "¡Error al enviar el correo electrónico de restablecimiento!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            break;
        }

    }

    //Metodo para iniciar sesion en firebase enviandole el email y la contraseña
    public void iniciarSesion(){
        progressDialog.setMessage("Comprobando credenciales, por favor espere.");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(editEmail.getText().toString().trim(),editContrasena.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "No se pudo iniciar sesion", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();}
            }
        });

    }

}
