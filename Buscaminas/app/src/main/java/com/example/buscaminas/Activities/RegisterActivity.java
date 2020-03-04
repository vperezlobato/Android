package com.example.buscaminas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registrar;
    private EditText email,usuario,password,confirmPassword;
    private TextView alreadyHaveAcc;
    private String txtEmail,txtUsuario,txtPassword, txtConfirmPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.editRegisterEmail);
        usuario = findViewById(R.id.editRegisterNombre);
        password = findViewById(R.id.editRegisterContrasena);
        confirmPassword = findViewById(R.id.editRegisterConfirmacionContrasena);
        registrar = findViewById(R.id.btnRegisterRegister);
        alreadyHaveAcc = findViewById(R.id.textAlreadyHave);
        progressDialog = new ProgressDialog(this);

        //Metodo onclick que te envia directamente al login si pinchas en el label "Already have an account"
        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
            }
        });

        registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        txtEmail = email.getText().toString();
        txtUsuario = usuario.getText().toString();
        txtPassword = password.getText().toString();
        txtConfirmPassword = confirmPassword.getText().toString();

        if(!txtEmail.isEmpty() && !txtUsuario.isEmpty() && !txtPassword.isEmpty() && !txtConfirmPassword.isEmpty()){ //Si hay algun campo vacio no entra
            if(txtPassword.length() >= 6 && txtConfirmPassword.length() >= 6) { //La password y la password de confirmacion deben tener 6 o mas caracteres o digitos
                if (txtPassword.equals(txtConfirmPassword)) { //Si la password y la password de confirmacion son iguales
                    registerUser();
                } else
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Las contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
    }

    //Metodo para registrar un nuevo usuario en firebase
    public void registerUser(){
        progressDialog.setMessage("Se estan procesando los datos, por favor espere.");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //Si lo ha registrado con exito guarda su nombre en firebase database
                    Map<String,Object> map = new HashMap<>();
                    map.put("usuario",txtUsuario);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                            }else
                                Toast.makeText(RegisterActivity.this, "No se han podido crear los datos correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                if (task.getException() instanceof FirebaseAuthUserCollisionException) { //En el caso de que ya exista el correo con el que intenta registrarse
                    Toast.makeText(RegisterActivity.this, "Ya existe un usuario con ese email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }
}
