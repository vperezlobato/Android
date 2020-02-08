package com.example.buscaminas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.buscaminas.Partida;
import com.example.buscaminas.R;

import java.util.ArrayList;

public class listaClasificacionAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Partida> listaPartidas;

    public listaClasificacionAdapter(Context context, ArrayList<Partida> listaPartidas) {
        this.context = context;
        this.listaPartidas = listaPartidas;
    }

    @Override
    public int getCount() {
        int size;
        if(listaPartidas == null){
            size = 0;
        }else{
            size = listaPartidas.size();
        }

        return size;
    }

    @Override
    public Object getItem(int position) {
        return listaPartidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView titulonick;
        TextView partidasGanadas;
        TextView partidasJugadas;
        ViewholderLista holder;
        View row = convertView;

        Partida partida = listaPartidas.get(position);

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.filaclasificacion, parent, false);

            titulonick = row.findViewById(R.id.nick);
            partidasGanadas = row.findViewById(R.id.partidasGanadas);
            partidasJugadas = row.findViewById(R.id.partidasJugadas);
            holder = new ViewholderLista( partidasGanadas,partidasJugadas,titulonick);

            row.setTag(holder);
        }else{
            holder = (ViewholderLista) row.getTag();
        }

        if(partida != null) {
            holder.getNick().setText(partida.getUsuario());
            holder.getPartidasGanadas().setText(String.valueOf(partida.getNumeroPartidasGanadas()));
            holder.getPartidasJugadas().setText(String.valueOf(partida.getNumeroPartidas()));
        }
        return row;
    }
}
