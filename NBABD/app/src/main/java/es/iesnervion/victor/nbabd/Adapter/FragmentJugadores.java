package es.iesnervion.victor.nbabd.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.iesnervion.victor.nbabd.Clases.Jugador;
import es.iesnervion.victor.nbabd.R;
import es.iesnervion.victor.nbabd.ViewHolder.ViewHolderJugadores;

public class FragmentJugadores extends Fragment {
    String nombre;
    String apellido;
    Jugador jugador;

    public FragmentJugadores(){}

    public static FragmentJugadores newInstance(List<Jugador> jugadores, int val) {

        FragmentJugadores fragmentFirst = new FragmentJugadores();

        Jugador jugador = new Jugador();
        jugador = jugadores.get(val);

        Bundle args = new Bundle();
        args.putInt("val", val);
        args.putString("nombre", jugador.getFirstName());
        args.putString("apellido", jugador.getLastName());
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nombre = getArguments().getString("nombre");
        apellido = getArguments().getString("apellido");
        this.jugador = new Jugador(nombre,apellido);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewHolderJugadores holder;
        TextView nombre;
        TextView apellido;

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.custom_layout, container, false);

        nombre = rootView.findViewById(R.id.NombreJugador);
        apellido = rootView.findViewById(R.id.ApellidoJugador);

        holder = new ViewHolderJugadores(nombre, apellido);
        rootView.setTag(holder);

        holder.getNombre().setText(jugador.getFirstName());
        holder.getApellido().setText(jugador.getLastName());

        return rootView;
    }
}