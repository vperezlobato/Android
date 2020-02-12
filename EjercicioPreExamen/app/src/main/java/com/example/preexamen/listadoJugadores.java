package com.example.preexamen;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class listadoJugadores {

    public MutableLiveData<ArrayList<Object>> listaJugadores(){
        ArrayList<Object> lista = new ArrayList<Object>();
        MutableLiveData<ArrayList<Object>> jugadores = new MutableLiveData<ArrayList<Object>>();


        ArrayList<String> posicionesFutbolista = new ArrayList<String>();
        posicionesFutbolista.add("Delantero");
        posicionesFutbolista.add("Defensa");
        lista.add(new clsJugadorFutbol("Romelu Lukaku", R.drawable.romelu_lukaku_f, "Delantero", posicionesFutbolista, 1));


        ArrayList<String> posicionesFutbolista2 = new ArrayList<String>();
        posicionesFutbolista2.add("Centrocampista");
        posicionesFutbolista2.add("Defensa");
        lista.add(new clsJugadorFutbol("Kevin de Bruyne", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 2));

        lista.add(new clsJugadorFutbol("Kevin de Bruyne_2", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 3));

        lista.add(new clsJugadorBaloncesto("Lebron James", R.drawable.lebron_james_b, 30, 10, 3, 4));
        lista.add(new clsJugadorBaloncesto("Marc Gasol", R.drawable.marc_gasol_b, 20, 13, 8, 5));
        lista.add(new clsJugadorBaloncesto("Pau Gasol", R.drawable.pau_gasol_b, 34, 34, 34, 6));

        lista.add(new clsJugadorFutbol("Kevin de Bruyne_3", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 7));

        lista.add(new clsJugadorBaloncesto("Marc Gasol_2", R.drawable.marc_gasol_b, 20, 13, 8, 8));

        ArrayList<String> posicionesFutbolista3 = new ArrayList<String>();
        posicionesFutbolista.add("Delantero derecho");
        posicionesFutbolista.add("Defensa");
        posicionesFutbolista.add("Portero");
        lista.add(new clsJugadorFutbol("Romelu Lukaku_2", R.drawable.romelu_lukaku_f, "Delantero derecho", posicionesFutbolista3, 9));

        lista.add(new clsJugadorFutbol("Romelu Lukaku", R.drawable.romelu_lukaku_f, "Delantero", posicionesFutbolista, 10));

        lista.add(new clsJugadorFutbol("Kevin de Bruyne", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 11));

        lista.add(new clsJugadorFutbol("Kevin de Bruyne_2", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 12));

        lista.add(new clsJugadorBaloncesto("Lebron James", R.drawable.lebron_james_b, 30, 10, 3, 13));
        lista.add(new clsJugadorBaloncesto("Marc Gasol", R.drawable.marc_gasol_b, 20, 13, 8, 14));
        lista.add(new clsJugadorBaloncesto("Pau Gasol", R.drawable.pau_gasol_b, 34, 34, 34, 15));

        lista.add(new clsJugadorFutbol("Kevin de Bruyne_3", R.drawable.kevin_de_bruyne_f, "Centrocampista", posicionesFutbolista2, 16));
        posicionesFutbolista.clear();

        lista.add(new clsJugadorBaloncesto("Marc Gasol_2", R.drawable.marc_gasol_b, 20, 13, 8, 17));

        lista.add(new clsJugadorFutbol("Romelu Lukaku_2", R.drawable.romelu_lukaku_f, "Delantero derecho", posicionesFutbolista3, 18));

        jugadores.setValue(lista);

        return jugadores;
    }
}
