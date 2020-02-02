package com.example.buscaminas;

public class tablero {

    private clsCasilla tablero[][];
    private int altura;
    private int ancho;

    public tablero(int altura,int ancho){
        this.altura = altura;
        this.ancho = ancho;
        tablero = new clsCasilla[altura][ancho];

        for(int i = 0; i < altura;i++){
            for(int j = 0; j < ancho; j++){
                tablero[i][j] = new clsCasilla(i,j);
            }
        }
    }

    public int getAltura() {
        return altura;
    }

    public int getAncho() {
        return ancho;
    }

    public clsCasilla[][] getTablero() {
        return tablero;
    }
}
