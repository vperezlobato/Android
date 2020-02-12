package com.example.preexamen;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderBaloncestista {

    private ImageView foto;
    private TextView nombre;
    private TextView puntos;
    private TextView rebotes;
    private TextView asistencias;

    public ViewHolderBaloncestista(){}

    public ViewHolderBaloncestista (ImageView foto, TextView nombre, TextView puntos, TextView rebotes, TextView asistencias){
        this.foto = foto;
        this.nombre = nombre;
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;

    }

    public TextView getNombre() {
        return nombre;
    }

    public ImageView getFoto() {
        return foto;
    }

    public TextView getAsistencias() {
        return asistencias;
    }

    public TextView getPuntos() {
        return puntos;
    }

    public TextView getRebotes() {
        return rebotes;
    }
}
