package es.iesnervion.victor.nba.ViewHolder;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {

    private TextView nombreEquipo,abreviacion,nombreSimple,localizacion;
    private ImageView icono;

    public ViewHolder(TextView nombreEquipo,ImageView icono,TextView abreviacion,TextView nombreSimple,TextView localizacion){
        this.nombreEquipo = nombreEquipo;
        this.icono = icono;
        this.abreviacion = abreviacion;
        this.nombreSimple = nombreSimple;
        this.localizacion = localizacion;
    }

    public TextView getNombreEquipo(){
        return this.nombreEquipo;
    }

    public TextView getAbreviacion() {
        return abreviacion;
    }

    public TextView getLocalizacion() {
        return localizacion;
    }

    public TextView getNombreSimple() {
        return nombreSimple;
    }

    public ImageView getIcono() {
            return this.icono;
        }

}




