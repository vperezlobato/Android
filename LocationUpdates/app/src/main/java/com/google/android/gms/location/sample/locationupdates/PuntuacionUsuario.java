package com.google.android.gms.location.sample.locationupdates;

public class PuntuacionUsuario
{
    private int puntos;
    private String usuario;

    public PuntuacionUsuario(int puntos, String usuario)
    {
        this.puntos = puntos;
        this.usuario = usuario;
    }

    public int getPuntos()
    {
        return puntos;
    }

    public void setPuntos(int puntos)
    {
        this.puntos = puntos;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }
}
