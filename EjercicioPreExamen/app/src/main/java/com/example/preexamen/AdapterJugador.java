package com.example.preexamen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterJugador extends BaseAdapter {

    private Context context;
    private List<Object> jugadores;

    public AdapterJugador(Context context, List<Object> jugadores){

        this.context = context;
        this.jugadores = jugadores;
    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    @Override
    public Object getItem(int position) {
        return jugadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        long id;

        if(jugadores.get(position) instanceof clsJugadorFutbol){
            id = ((clsJugadorFutbol) jugadores.get(position)).getId();
        }else
            id = ((clsJugadorBaloncesto) jugadores.get(position)).getId();

        return id;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int posicion) {
        int devolver;

        if(jugadores.get(posicion) instanceof clsJugadorFutbol)
        {
            devolver = 0;
        }
        else
        {
            devolver = 1;
        }

        return devolver;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TextView nombreB,puntos,asistencias,rebotes; //baloncestista
        TextView nombreF,posicion; //futbolista
        ImageView imagenB,imagenF;
        ViewHolderBaloncestista holderB = new ViewHolderBaloncestista();
        ViewHolderFutbolista holderF = new ViewHolderFutbolista();
        clsJugadorFutbol futbolista;
        clsJugadorBaloncesto baloncestista;

        if(row == null){
            LayoutInflater inflador = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            if(getItemViewType(position) == 0){
                row = inflador.inflate(R.layout.fila_futbolista, parent, false);
                imagenF = row.findViewById(R.id.imgFoto);
                posicion = row.findViewById(R.id.txvPosicion);
                nombreF = row.findViewById(R.id.txvNombreF);

                holderF = new ViewHolderFutbolista(nombreF,posicion,imagenF);

                row.setTag(holderF);
            }else{
                row = inflador.inflate(R.layout.fila_baloncesto, parent, false);
                nombreB = row.findViewById(R.id.txvNombreB);
                puntos = row.findViewById(R.id.txvPuntos);
                asistencias = row.findViewById(R.id.txvAsistencias);
                rebotes = row.findViewById(R.id.txvRebotes);
                imagenB = row.findViewById(R.id.imgFotoB);

                holderB = new ViewHolderBaloncestista(imagenB,nombreB,puntos,rebotes,asistencias);

                row.setTag(holderB);
            }
        }else
            if(getItemViewType(position) == 0){
                holderF = (ViewHolderFutbolista) row.getTag();
            }else
                holderB = (ViewHolderBaloncestista) row.getTag();


            if(getItemViewType(position) == 0){
                futbolista = (clsJugadorFutbol) jugadores.get(position);
                holderF.getImagen().setImageResource(futbolista.getFoto());
                holderF.getNombre().setText(futbolista.getNombre());
                holderF.getPosicion().setText(futbolista.getPosicionActual());
            }else{
                baloncestista = (clsJugadorBaloncesto) jugadores.get(position);
                holderB.getNombre().setText(baloncestista.getNombre());
                holderB.getAsistencias().setText(Integer.toString(baloncestista.getAsistenciasPorPartido()));
                holderB.getPuntos().setText(Integer.toString(baloncestista.getPuntosPorPartido()));
                holderB.getRebotes().setText(Integer.toString(baloncestista.getRebotesPorPartido()));
                holderB.getFoto().setImageResource(baloncestista.getFoto());
            }

        return row;
    }
}
