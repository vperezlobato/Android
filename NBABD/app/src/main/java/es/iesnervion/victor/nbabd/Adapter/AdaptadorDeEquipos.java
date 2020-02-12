package es.iesnervion.victor.nbabd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.iesnervion.victor.nbabd.Clases.Equipo;
import es.iesnervion.victor.nbabd.R;
import es.iesnervion.victor.nbabd.ViewHolder.ViewHolderListaEquipo;

public class AdaptadorDeEquipos extends ArrayAdapter {
    public AdaptadorDeEquipos(Context context, int resourceId, List objects){
        super(context,resourceId,0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderListaEquipo holder;
        TextView nombreEquipo = null;

        View row =convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(row == null){
            row = inflater.inflate(R.layout.activity_listview_activity,parent,false);

            nombreEquipo = (TextView) row.findViewById(R.id.texto);

            holder = new ViewHolderListaEquipo(nombreEquipo);
            row.setTag(holder);
        }else
            holder = (ViewHolderListaEquipo) row.getTag();

        Equipo item = (Equipo)getItem(position);

        holder.getNombreEquipo().setText(item.getTeamName());

        return(row);
    }
}
