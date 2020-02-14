package com.example.buscaminas.Clases;

import java.util.Random;

public class Partida {

    private String dificultad;
    private int numeroPartidas;
    private int numeroPartidasGanadas;
    private String usuario;

    public Partida(){
        //ID = randomKey();
        usuario = "";
        dificultad = "";
        numeroPartidasGanadas = 0;
        numeroPartidas = 0;
    }

    public Partida(String dificultad, int numeroPartidas, int numeroPartidasGanadas){

        this.dificultad = dificultad;
        this.numeroPartidas = numeroPartidas;
        this.numeroPartidasGanadas = numeroPartidasGanadas;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNumeroPartidas() {
        return numeroPartidas;
    }

    public int getNumeroPartidasGanadas() {
        return numeroPartidasGanadas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setNumeroPartidas(int numeroPartidas) {
        this.numeroPartidas = numeroPartidas;
    }

    public void setNumeroPartidasGanadas(int numeroPartidasGanadas) {
        this.numeroPartidasGanadas = numeroPartidasGanadas;
    }

}
