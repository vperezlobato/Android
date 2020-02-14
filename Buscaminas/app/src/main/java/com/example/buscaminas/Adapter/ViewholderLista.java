package com.example.buscaminas.Adapter;

import android.widget.TextView;

public class ViewholderLista {

    private TextView partidasGanadas;
    private TextView partidasJugadas;
    private TextView nick;
    private TextView numero;
    public ViewholderLista(){}

    public ViewholderLista(TextView partidasGanadas,TextView partidasJugadas,TextView nick,TextView numero){
        this.partidasGanadas = partidasGanadas;
        this.partidasJugadas = partidasJugadas;
        this.nick = nick;
        this.numero = numero;
    }

    public TextView getNick() {
        return nick;
    }

    public void setNick(TextView nick) {
        this.nick = nick;
    }

    public TextView getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(TextView partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public TextView getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(TextView partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public TextView getNumero() {
        return numero;
    }

    public void setNumero(TextView numero) {
        this.numero = numero;
    }
}
