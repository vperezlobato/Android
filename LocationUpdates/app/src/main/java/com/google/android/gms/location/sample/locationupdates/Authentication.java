package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.sample.locationupdates.FirebaseDB.FirebaseDB;
import com.google.android.gms.location.sample.locationupdates.Fragment.FragmentLogin;
import com.google.android.gms.location.sample.locationupdates.Fragment.FragmentRegister;
import com.google.android.gms.location.sample.locationupdates.Interface.OnCreateUserRequestedListener;
import com.google.android.gms.location.sample.locationupdates.Interface.OnShowLoginRequestedListener;
import com.google.android.gms.location.sample.locationupdates.Interface.OnShowRegisterRequestedListener;
import com.google.android.gms.location.sample.locationupdates.Interface.OnSignInRequestedListener;
import com.google.android.gms.location.sample.locationupdates.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends FragmentActivity implements OnCreateUserRequestedListener, OnSignInRequestedListener, OnShowLoginRequestedListener, OnShowRegisterRequestedListener, OnCompleteListener<AuthResult>
{
    FirebaseAuth auth;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        auth = FirebaseAuth.getInstance();

        FirebaseDB db = new FirebaseDB(this);

        //db.setMessage();
        //db.getMessages();

        FragmentLogin fragmentLogin = new FragmentLogin();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentAuth, fragmentLogin)
                .commit();
    }

    public void onCreateUserRequested(String email, String password, final String username)
    {
        if(!Utils.stringIsNullOrEmpty(email) && !Utils.stringIsNullOrEmpty(password) && !Utils.stringIsNullOrEmpty(username))
        {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplication(), "Registro con exito", Toast.LENGTH_SHORT).show();

                            new FirebaseDB(getApplicationContext()).añadirUsuario(username, task.getResult().getUser().getUid());

                            //startActivity(new Intent(getApplication(), Login.class));

                            showLoginFragment();
                        }
                        else
                        {
                            Toast.makeText(getApplication(), "No te has podido registrar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Email, nombre de usuario y contraseña no pueden estar vacios", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void onSignInRequested(String email, String password)
    {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        hideKeyboard();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this);
    }

    private void hideKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task)
    {
        if(task.isSuccessful())
        {
            //new FirebaseDB(getApplicationContext()).añadirUsuario(this.username, auth.getCurrentUser().getUid());

            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("user", auth.getCurrentUser());

            startActivity(intent);

            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }
        else
        {
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Usuario o contraseña no validos", Toast.LENGTH_LONG).show();
        }
    }

    private void showLoginFragment()
    {
        FragmentLogin fragmentLogin = new FragmentLogin();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragmentAuth, fragmentLogin)
                .commit();
    }

    private void showRegisterFragment()
    {
        FragmentRegister fragmentRegister = new FragmentRegister();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragmentAuth, fragmentRegister)
                .commit();
    }



    @Override
    public void onShowLoginRequested()
    {
        showLoginFragment();
    }

    @Override
    public void onShowRegisterRequested()
    {
        showRegisterFragment();
    }
}
