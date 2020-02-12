package com.example.preexamen;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderFutbolista {

    private TextView nombre;
    private TextView posicion;
    private ImageView imagen;

    public ViewHolderFutbolista(){

    }
    public ViewHolderFutbolista(TextView nombre, TextView posicion, ImageView imagen){
        this.nombre = nombre;
        this.posicion = posicion;
        this.imagen = imagen;

    }

    public ImageView getImagen() {
        return imagen;
    }

    public TextView getNombre() {
        return nombre;
    }

    public TextView getPosicion() {
        return posicion;
    }
}
