package es.iesnervion.victor.nbabd.ViewHolder;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderJugadores {

    TextView nombre,apellido;

    public ViewHolderJugadores(TextView nombre,TextView apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public TextView getApellido() {
        return apellido;
    }

    public TextView getNombre() {
        return nombre;
    }

}
