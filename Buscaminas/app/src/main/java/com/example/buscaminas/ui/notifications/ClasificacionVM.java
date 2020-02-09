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
    private MutableLiveData<ArrayList<Partida>> listaPartidasAux;

    //Constructor por defecto
    public ClasificacionVM() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Propiedades publicas
    public MutableLiveData<ArrayList<Partida>> getListaPartidas() {
        if (listaPartidas == null) {
            listaPartidas = new MutableLiveData<>();
        }
        return listaPartidas;
    }

    public MutableLiveData<ArrayList<Partida>> getListaPartidasAux() {
        if(listaPartidasAux == null){
            listaPartidasAux = new MutableLiveData<>();
            cargarListadoPartidas();
        }

        return listaPartidasAux;
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
                listaPartidasAux.setValue(partidasAux);

                Collections.sort(listaPartidasAux.getValue(), new Comparator<Partida>() {
                    public int compare(Partida partida1, Partida partida2) {
                        return partida1.getNumeroPartidasGanadas() + (partida2.getNumeroPartidasGanadas());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void filtrarPorDificultad(String dificultad)
    {
        ArrayList<Partida> listaPartidas = new ArrayList<Partida>();
        if(getListaPartidasAux().getValue() != null){
            for (Partida partida : getListaPartidasAux().getValue()) {

                if (partida.getDificultad().equals(dificultad))
                    listaPartidas.add(partida);

            }
            getListaPartidas().setValue(listaPartidas);
        }



    }

}
