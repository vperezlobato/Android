package com.example.buscaminas.Clases;

import com.example.buscaminas.R;

public class clsCasilla {

    private Boolean esBomba;
    private Boolean banderaPuesta;
    private Boolean yaPulsada;
    private int imagen;
    private int id;
    private int numero;
    private int posX,posY;

    public clsCasilla(){
        esBomba = false;
        banderaPuesta = false;
        yaPulsada = false;
        imagen = R.drawable.casilla;
        id = 0;
        numero = 0;
        posX = 0;
        posY = 0;
    }

    public clsCasilla(int x,int y){
        esBomba = false;
        banderaPuesta = false;
        yaPulsada = false;
        imagen = R.drawable.casilla;
        id = 0;
        numero = 0;
        this.posX = x;
        this.posY = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Boolean getEsBomba() {
        return esBomba;
    }

    public void setEsBomba(Boolean esBomba) {
        this.esBomba = esBomba;
    }

    public Boolean getYaPulsada() {
        return yaPulsada;
    }

    public void setYaPulsada(Boolean yaPulsada) {
        this.yaPulsada = yaPulsada;
    }

    public int getImagen() {
        return imagen;
    }

    public Boolean getBanderaPuesta() {
        return banderaPuesta;
    }

    public void setBanderaPuesta(Boolean banderaPuesta) {
        this.banderaPuesta = banderaPuesta;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}
