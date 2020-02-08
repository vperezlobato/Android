package com.example.buscaminas.ui.notifications;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buscaminas.Partida;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClasificacionVM extends ViewModel {

    private MutableLiveData<ArrayList<Partida>> listaPartidas;
    private DatabaseReference mDatabase;

    //Constructor por defecto
    public ClasificacionVM() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Propiedades publicas
    public LiveData<ArrayList<Partida>> getListaPartidas() {
        if (listaPartidas == null) {
            listaPartidas = new MutableLiveData<>();
            cargarListadoPartidas();
        }
        return listaPartidas;
    }

    private void cargarListadoPartidas() {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(currentUser);
        mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Partida> partidasAux = new ArrayList<>();
                for (DataSnapshot usuario : dataSnapshot.getChildren()) {
                    for (DataSnapshot objSnapshot : usuario.getChildren()){
                            String user = usuario.child("usuario").getValue(String.class);
                            if(!objSnapshot.getKey().equals("usuario")){
                                Partida partida = objSnapshot.getValue(Partida.class);
                                partida.setUsuario(user);
                                partidasAux.add(partida);
                            }
                    }
                }

                listaPartidas.setValue(partidasAux);

                //Ordenamos por fecha, asi la mas reciente sale primero
                Collections.sort(listaPartidas.getValue(), new Comparator<Partida>() {
                    public int compare(Partida o1, Partida o2) {
                        return o1.getNumeroPartidasGanadas() - (o2.getNumeroPartidasGanadas());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
