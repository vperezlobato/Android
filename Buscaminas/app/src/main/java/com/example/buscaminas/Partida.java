package com.example.buscaminas;

import java.util.Random;

public class Partida {

    private String dificultad;
    private int numeroPartidas;
    private int numeroPartidasGanadas;
    private String ID;
    private static final String Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public Partida(){
        ID = randomKey();
        dificultad = "";
        numeroPartidasGanadas = 0;
        numeroPartidas = 0;
    }

    public Partida(String dificultad, int numeroPartidas, int numeroPartidasGanadas){

        this.dificultad = dificultad;
        this.numeroPartidas = numeroPartidas;
        this.numeroPartidasGanadas = numeroPartidasGanadas;
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

    public String getID() {
        return ID;
    }

    public String randomKey(){
        StringBuilder key = new StringBuilder();
        Random rd = new Random();

        for(int i = 0; i < 10; i++){
            key.append(Characters.charAt(rd.nextInt(Characters.length())));
        }

        return key.toString();
    }
}
