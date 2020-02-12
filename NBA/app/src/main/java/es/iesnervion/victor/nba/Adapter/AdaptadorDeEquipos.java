package es.iesnervion.victor.nba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.iesnervion.victor.nba.Clases.Equipo;
import es.iesnervion.victor.nba.R;
import es.iesnervion.victor.nba.ViewHolder.ViewHolder;

public class AdaptadorDeEquipos extends ArrayAdapter {
    public AdaptadorDeEquipos(Context context, int resourceId, List objects){
        super(context,resourceId,0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        TextView nombreEquipo = null,abreviacion = null,nombreSimple = null,localizacion = null;
        ImageView icono;

        View row =convertView;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(row == null){
            row = inflater.inflate(R.layout.activity_listview_activity,parent,false);

            nombreEquipo = (TextView) row.findViewById(R.id.texto);
            abreviacion = (TextView) row.findViewById(R.id.abreviacion);
            nombreSimple = (TextView) row.findViewById(R.id.nombreSimple);
            localizacion = (TextView) row.findViewById(R.id.localizacion);
            icono = (ImageView) row.findViewById(R.id.icon);

            holder = new ViewHolder(nombreEquipo,icono,abreviacion,nombreSimple,localizacion);
            row.setTag(holder);
        }else
            holder = (ViewHolder) row.getTag();

        Equipo item = (Equipo)getItem(position);

        holder.getAbreviacion().setText(item.getAbbreviation());
        holder.getNombreEquipo().setText(item.getTeamName());
        holder.getLocalizacion().setText(item.getLocation());
        holder.getNombreSimple().setText(item.getSimpleName());
        holder.getIcono().setImageResource(convertirRutaEnId(item.getImagen()));

        return(row);
    }

    private int convertirRutaEnId(String nombre){
        Context context = getContext();
        return context.getResources().getIdentifier(nombre, "drawable", context.getPackageName());
    }
}
