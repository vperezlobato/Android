package com.example.buscaminas;

import android.media.Image;

public class clsCasilla {

    private Boolean esBomba;
    private Boolean banderaPuesta;
    private Boolean yaPulsada;
    private Image imagen;

    public clsCasilla(){
        esBomba = false;
        banderaPuesta = false;
        yaPulsada = false;


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

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public Boolean getBanderaPuesta() {
        return banderaPuesta;
    }

    public void setBanderaPuesta(Boolean banderaPuesta) {
        this.banderaPuesta = banderaPuesta;
    }

}
