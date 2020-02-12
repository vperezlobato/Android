package com.example.preexamen;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class clsJugadorFutbol implements Parcelable {

    private String nombre;
    private int foto;
    private String posicionActual;
    private ArrayList<String> posiciones;
    private long id;

    public clsJugadorFutbol(String nombre, int foto, String nPosicionActual, ArrayList<String> posiciones, long nId) {
        this.nombre = nombre;
        this.foto = foto;
        this.posicionActual = nPosicionActual;
        this.posiciones = posiciones;
        this.id=nId;
    }

    protected clsJugadorFutbol(Parcel in) {
        nombre = in.readString();
        foto = in.readInt();
        posicionActual = in.readString();
        posiciones = in.createStringArrayList();
        id = in.readLong();
    }

    public static final Creator<clsJugadorFutbol> CREATOR = new Creator<clsJugadorFutbol>() {
        @Override
        public clsJugadorFutbol createFromParcel(Parcel in) {
            return new clsJugadorFutbol(in);
        }

        @Override
        public clsJugadorFutbol[] newArray(int size) {
            return new clsJugadorFutbol[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public ArrayList<String> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(ArrayList<String> posiciones) {
        this.posiciones = posiciones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(String posicionActual) {
        this.posicionActual = posicionActual;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(foto);
        parcel.writeString(posicionActual);
        parcel.writeStringList(posiciones);
        parcel.writeLong(id);
    }
}
