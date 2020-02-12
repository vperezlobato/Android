package com.example.preexamen;

import android.os.Parcel;
import android.os.Parcelable;

public class clsJugadorBaloncesto implements Parcelable
{

    private String nombre;
    private int foto;
    private int puntosPorPartido;
    private int rebotesPorPartido;
    private int asistenciasPorPartido;
    private long id;

    public clsJugadorBaloncesto(String nombre, int foto, int puntosPorPartido, int rebotesPorPartido, int asistenciasPorPartido, long nId) {
        this.nombre = nombre;
        this.foto = foto;
        this.puntosPorPartido = puntosPorPartido;
        this.rebotesPorPartido = rebotesPorPartido;
        this.asistenciasPorPartido = asistenciasPorPartido;
        this.id=nId;
    }

    protected clsJugadorBaloncesto(Parcel in) {
        nombre = in.readString();
        foto = in.readInt();
        puntosPorPartido = in.readInt();
        rebotesPorPartido = in.readInt();
        asistenciasPorPartido = in.readInt();
        id = in.readLong();
    }

    public static final Creator<clsJugadorBaloncesto> CREATOR = new Creator<clsJugadorBaloncesto>() {
        @Override
        public clsJugadorBaloncesto createFromParcel(Parcel in) {
            return new clsJugadorBaloncesto(in);
        }

        @Override
        public clsJugadorBaloncesto[] newArray(int size) {
            return new clsJugadorBaloncesto[size];
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

    public int getPuntosPorPartido() {
        return puntosPorPartido;
    }

    public void setPuntosPorPartido(int puntosPorPartido) {
        this.puntosPorPartido = puntosPorPartido;
    }

    public int getRebotesPorPartido() {
        return rebotesPorPartido;
    }

    public void setRebotesPorPartido(int rebotesPorPartido) {
        this.rebotesPorPartido = rebotesPorPartido;
    }

    public int getAsistenciasPorPartido() {
        return asistenciasPorPartido;
    }

    public void setAsistenciasPorPartido(int asistenciasPorPartido) {
        this.asistenciasPorPartido = asistenciasPorPartido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(foto);
        parcel.writeInt(puntosPorPartido);
        parcel.writeInt(rebotesPorPartido);
        parcel.writeInt(asistenciasPorPartido);
        parcel.writeLong(id);
    }
}
