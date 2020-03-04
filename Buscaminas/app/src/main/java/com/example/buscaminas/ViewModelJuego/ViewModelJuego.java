package com.example.buscaminas.ViewModelJuego;

import androidx.lifecycle.ViewModel;

import com.example.buscaminas.Clases.tablero;
import com.example.buscaminas.Manejadora.ManejadorJuego;

public class ViewModelJuego extends ViewModel {

    private int numeroMinas;
    private com.example.buscaminas.Clases.tablero tablero;
    private int altura;
    private int ancho;
    private boolean jugando;
    private boolean primerClick;

    public ViewModelJuego(){

        this.altura = 0;
        this.ancho = 0;
        this.numeroMinas = 0;
        this.jugando = true;
        this.primerClick = true;
    }

    public boolean isJugando() {
        return jugando;
    }

    public boolean isPrimerClick() {
        return primerClick;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public void setPrimerClick(boolean primerClick) {
        this.primerClick = primerClick;
    }

    public int getNumeroMinas() {
        return numeroMinas;
    }

    public void setNumeroMinas(int numeroMinas) {
        this.numeroMinas = numeroMinas;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public tablero getTablero() {
        return tablero;
    }

    public void crearPartida(){
        ManejadorJuego mj = new ManejadorJuego();
        tablero = new tablero(altura,ancho);

        mj.establecerMinas(numeroMinas,altura,ancho,tablero);

        mj.ponerNumerosEnLasCasillas(altura,ancho,tablero);
    }

}
