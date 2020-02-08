package com.example.buscaminas.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.buscaminas.Adapter.listaClasificacionAdapter;
import com.example.buscaminas.Partida;
import com.example.buscaminas.R;

import java.util.ArrayList;

public class ClasificacionFragment extends Fragment {

    private ListView lista;
    private ClasificacionVM vm;
    private listaClasificacionAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        lista = root.findViewById(R.id.lista);

        vm = ViewModelProviders.of(this).get(ClasificacionVM.class);

        //Creamos el Observer
        androidx.lifecycle.Observer<ArrayList<Partida>> listaObserver = new Observer<ArrayList<Partida>>() {
            @Override
            public void onChanged(ArrayList<Partida> listContact) {
                //Actualizar la UI
                adapter = new listaClasificacionAdapter(getActivity(), vm.getListaPartidas().getValue() );
                lista.setAdapter(adapter);
            }
        };

        vm.getListaPartidas().observe(getActivity(), listaObserver);
        return root;
    }
}