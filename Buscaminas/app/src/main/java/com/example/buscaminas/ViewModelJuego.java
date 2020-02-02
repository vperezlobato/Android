package com.example.buscaminas;

import androidx.lifecycle.ViewModel;

public class ViewModelJuego extends ViewModel {

    private int numeroMinas;
    private tablero tablero;
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
        tablero = new tablero(altura,ancho);

        establecerMinas();

        ponerNumerosEnLasCasillas();
    }

    public void establecerMinas() {
        int altura = 0;
        int ancho = 0;
        int cantidad = numeroMinas;
        for(int i= cantidad; cantidad != 0; cantidad-- ){
            altura = (int) (Math.random() * (this.altura-1));
            ancho = (int) (Math.random() * (this.ancho-1));
            if (tablero.getTablero()[altura][ancho].getEsBomba() == false) {
                tablero.getTablero()[altura][ancho].setEsBomba(true);
            }else
                cantidad++;
        }
    }

    public void ponerNumerosEnLasCasillas() {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < ancho; j++) {
                if (tablero.getTablero()[i][j].getEsBomba() == false) {
                    int cantidad = bombasAlrededorDeLaCasilla(i, j);
                    tablero.getTablero()[i][j].setNumero(cantidad);
                }
            }
        }
    }

    public int bombasAlrededorDeLaCasilla(int fila, int columna) {
        int total = 0;
        if (fila - 1 >= 0 && columna - 1 >= 0) {
            if (tablero.getTablero()[fila - 1][columna - 1].getEsBomba())
                total++;
        }
        if (fila - 1 >= 0) {
            if (tablero.getTablero()[fila - 1][columna].getEsBomba())
                total++;
        }
        if (fila - 1 >= 0 && columna + 1 < ancho) {
            if (tablero.getTablero()[fila - 1][columna + 1].getEsBomba())
                total++;
        }
        if (columna + 1 < ancho) {
            if (tablero.getTablero()[fila][columna + 1].getEsBomba())
                total++;
        }
        if (fila + 1 < altura && columna + 1 < ancho) {
            if (tablero.getTablero()[fila + 1][columna + 1].getEsBomba())
                total++;
        }
        if (fila + 1 < altura) {
            if (tablero.getTablero()[fila + 1][columna].getEsBomba())
                total++;
        }
        if (fila + 1 < altura && columna - 1 >= 0) {
            if (tablero.getTablero()[fila + 1][columna - 1].getEsBomba())
                total++;
        }
        if (columna - 1 >= 0) {
            if (tablero.getTablero()[fila][columna - 1].getEsBomba())
                total++;
        }
        return total;
    }
}
