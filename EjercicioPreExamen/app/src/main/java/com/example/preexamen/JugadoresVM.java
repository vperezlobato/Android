package com.example.preexamen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class JugadoresVM extends ViewModel {
    private MutableLiveData<ArrayList<Object>> jugadores;

    public MutableLiveData<ArrayList<Object>> getJugadores() {
        listadoJugadores listado = new listadoJugadores();
        if(jugadores == null){

            jugadores = listado.listaJugadores();
        }

        return jugadores;
    }

    public void EliminarJugador(int id){
        jugadores.getValue().remove(id);
    }


}
