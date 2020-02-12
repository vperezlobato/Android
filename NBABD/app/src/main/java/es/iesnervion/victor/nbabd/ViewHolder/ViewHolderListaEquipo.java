package es.iesnervion.victor.nbabd.ViewHolder;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolderListaEquipo {

    private TextView nombreEquipo;

    public ViewHolderListaEquipo(TextView nombreEquipo){
        this.nombreEquipo = nombreEquipo;
    }

    public TextView getNombreEquipo(){
        return this.nombreEquipo;
    }

}




