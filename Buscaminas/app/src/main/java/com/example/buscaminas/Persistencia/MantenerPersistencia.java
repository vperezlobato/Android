package com.example.buscaminas.Persistencia;

import com.google.firebase.database.FirebaseDatabase;

public class MantenerPersistencia extends android.app.Application{

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
