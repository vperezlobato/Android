package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.buscaminas.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin,btnRegister;
    private EditText editNombre,editContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnLogin:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            break;
            case R.id.btnRegister:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
            break;

        }

    }
}
